package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.serialization.Revision;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Saga
@Revision("2")
public class Context implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(Context.class);
    private static final String MANUALLY_PROVIDED = "MANUALLY PROVIDED";

    private transient OrderValidationAdapter orderValidationAdapter;
    private transient Lab400RequestAdapter lab400RequestAdapter;
    private transient CommandGateway commandGateway;

    private final Map<AnalysisNumber, POCT> poctTests = new HashMap<>();
    private final Map<User, POCT> toBeDeleted = new HashMap<>();
    private String orderId, institute;
    private InternalAnalysisAggregateId internalAnalysisAggregateId;
    private ContextState state;

    public Context() {
        this.state = new InitializedState();
        LOG.trace("Initial state: {}", this.state);
    }

    void nextState(ContextState next, String reason) {
        LOG.trace("State change: {} -> {} due to {}", this.state, next, reason);
        this.state = next;
        this.state.onStateEntry(this);
    }

    boolean isResultPresent() {
        return this.poctTests.values().stream().anyMatch(poct -> !StringUtils.isEmpty(poct.getValue()));
    }

    boolean hasPoctTests() {
        return !poctTests.isEmpty();
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(InternalAnalysisAggregateCreatedEvent event) {
        this.internalAnalysisAggregateId = event.getIdentifier();
        this.orderId = event.getOrderIdentification();

        SagaLifecycle.associateWith("orderId", event.getOrderIdentification());
    }

    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(InternalAnalysisAddedEvent event) {
        try {
            removeFromToBeDeleted(event.getAnalysisNumber());
            poctTests.put(event.getAnalysisNumber(), new POCT(event.getAnalysisNumber(), null, event.getUser()));
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("InternalAnalysisAddedEvent", e);
        }
    }


    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(ResultChangedEvent event) {
        try {
            changeResult(event.getAnalysisNumber(), event.getResult(), event.getUser(), MANUALLY_PROVIDED);
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("ResultChangedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(ResultLinkedEvent event) {
        try {
            removeFromToBeDeleted(event.getAnalysisNumber());
            changeResult(event.getAnalysisNumber(), event.getResult(), event.getUser(), event.getDevice());
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("ResultLinkedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(ResultAutoMatchedEvent event) {
        try {
            removeFromToBeDeleted(event.getAnalysisNumber());
            changeResult(event.getAnalysisNumber(), event.getResult(), event.getUser(), event.getDevice());
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("ResultAutomatchedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(ResultUnlinkedEvent event) {
        try {
            changeResult(event.getAnalysisNumber(), ResultValue.TOUCHED_BUT_NO_RESULT, event.getUser(), MANUALLY_PROVIDED);
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("ResultUnlinkedEvent", e);
        }
    }

    private void removeFromToBeDeleted(AnalysisNumber analysisNumber) {
        toBeDeleted.entrySet().removeIf(entry -> entry.getValue().getAnalysis().equals(analysisNumber));
    }

    private void changeResult(AnalysisNumber analysisNumber, String result, String user, String device) {
        POCT existingPoct = poctTests.get(analysisNumber);

        POCT poctToSave = new POCT(analysisNumber, result, user, device);

        if (existingPoct != null) {
            poctToSave.setAddedToRequest(poctToSave.isAddedToRequest());
            poctToSave.setAvailableForValidation(poctToSave.isAvailableForValidation());
        }

        poctTests.put(analysisNumber, poctToSave);
    }

    @SagaEventHandler(associationProperty = "identifier", keyName = "internalAnalysisAggregateId")
    public void handle(InternalAnalysisRemovedEvent event) {
        try {
            final POCT removed = poctTests.remove(event.getAnalysisNumber());
            if (removed != null) {
                toBeDeleted.put(new User(event.getUser()), removed);
            }
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("InternalAnalysisRemovedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "orderId")
    public void handle(OrderConfirmedEvent event) {
        try {
            this.orderId = event.getOrderId();
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("OrderConfirmedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "orderId")
    public void handle(OrderAutoValidatedEvent event) {
        try {
            this.institute = event.getInstitute();
            this.orderId = event.getOrderId();
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("OrderAutoValidatedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "orderId")
    public void handle(OrderValidatedEvent event) {
        try {
            this.institute = event.getInstitute();
            this.orderId = event.getOrderId();
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("OrderValidatedEvent", e);
        }
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "orderId")
    public void handle(OrderTubeValidatedEvent event) {
        try {
            this.institute = event.getInstitute();
            this.orderId = event.getOrderId();
            state.onEvent(this, event);
        } catch (RuntimeException e) {
            logAndRethrowException("OrderTubeValidatedEvent", e);
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId", keyName = "orderId")
    public void handle(OrderFinalizedEvent event) {
//        try {
        LOG.info("Terminating saga for {}", event.getIdentifier());
//            commandGateway.sendAndWait(new FinalizeInternalAnalysisAggregateCommand(this.internalAnalysisAggregateId));
//        } catch (RuntimeException e) {
//            logAndRethrowException("OrderFinalizedEvent", e);
//        }
    }

    public void registerPOCTAnalysesForValidation() {
        final List<POCT> toBeRecorded = this.poctTests.values().stream()
                .filter(poct -> !poct.isReadyForValidation())
                .collect(Collectors.toList());
        orderValidationAdapter.registerPOCTAnalyses(this.institute, this.orderId, toBeRecorded.stream().map(POCT::getAnalysis).collect(Collectors.toSet()), true);
        toBeRecorded.forEach(POCT::readyForValidation);
    }

    public void enableAutoValidation() {
        this.orderValidationAdapter.enableAutoValidationFor(this.institute, this.orderId, this.poctTests.keySet());
    }

    public void transmitResultValues() {
        for (POCT entry : poctTests.values()) {
            try {
                if (entry.hasResult() && !entry.isAddedToRequest()) {
                    this.lab400RequestAdapter.transmitResultValue(this.institute, this.orderId, entry.getAnalysis(), entry.getValue(), entry.getDevice(), entry.getUser());
                    entry.addedToRequest();
                } else if (!entry.isReadyForValidation()) {
                    // perhaps a POCT analysis was added while waiting for auto validation to complete?
                    this.lab400RequestAdapter.transmitAnalysis(this.institute, this.orderId, entry.getAnalysis(), entry.getUser());
                }
            } catch (SynchronisationFailureException syncException) {
                LOG.warn("Was not able to sync result {} for analysis {} with Lab400", entry.getValue(), entry.getAnalysis());
            }
        }
        removeAnalysis();
    }

    public void removeAnalysis() {
        for (Map.Entry<User, POCT> entry : this.toBeDeleted.entrySet()) {
            this.lab400RequestAdapter.removeAnalysis(this.institute, this.orderId, entry.getValue().getAnalysis(), entry.getKey().getName());
        }
        this.toBeDeleted.clear();
    }

    public void transmitAnalyses() {
        for (POCT entry : poctTests.values()) {
            if (!entry.isAddedToRequest()) {
                this.lab400RequestAdapter.transmitAnalysis(this.institute, this.orderId, entry.getAnalysis(), entry.getUser());
                entry.addedToRequest();
            }
        }
    }

    public void unregisterAnalysisFromValidation(AnalysisNumber analysis) {
        this.orderValidationAdapter.unregisterPOCTAnalysis(this.institute, this.orderId, analysis);
    }

    @Autowired
    public void setOrderValidationAdapter(OrderValidationAdapter orderValidationAdapter) {
        this.orderValidationAdapter = orderValidationAdapter;
    }

    @Autowired
    public void setLab400RequestAdapter(Lab400RequestAdapter lab400RequestAdapter) {
        this.lab400RequestAdapter = lab400RequestAdapter;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Autowired
    public void setVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    private void logAndRethrowException(String event, RuntimeException e) {
        if (LOG.isErrorEnabled()) {
            LOG.error(String.format("Error handling %s for order: %s, internal analysis aggregate: %s", event, orderId, internalAnalysisAggregateId), e);
        }
        throw e;
    }

    public interface Visitor {
        void visit(Context context);
    }

    public static class NullValueDeletingVisitor implements Visitor {
        @Override
        public void visit(Context context) {
            context.toBeDeleted.entrySet().removeIf(entry -> entry.getValue() == null);
        }
    }
}
