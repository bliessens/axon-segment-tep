package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class OrderConfirmedState extends ContextState {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConfirmedState.class);

    private final boolean firstResultIsKnown;

    public OrderConfirmedState(boolean firstResultIsKnown) {
        this.firstResultIsKnown = firstResultIsKnown;
    }

    @Override
    void onStateEntry(Context context) {
        context.registerPOCTAnalysesForValidation();
        LOG.trace(getClass().getSimpleName() + ".onStateEntry() {"); // NOSONAR
        LOG.trace("\tAdd ALL POCT analyses with flag3='N' in ORDDTL");
        LOG.trace("}");

        if (firstResultIsKnown) {
            context.enableAutoValidation();
            LOG.trace(getClass().getSimpleName() + ".onStateEntry() {"); // NOSONAR
            LOG.trace("\t(First POCT result value is available for this order)");
            LOG.trace("\tEnable Auto Validation for this order with flag3='y' in ORDHDR");
            LOG.trace("\tAdd ALL POCT analyses with flag3='y' in ORDDTL");
            LOG.trace("}");
        }

    }

    @Override
    void onEvent(Context context, ResultChangedEvent event) {
        goToAutoValidationState(context, ResultChangedEvent.class.getSimpleName());
    }

    @Override
    void onEvent(Context context, ResultLinkedEvent event) {
        registerPoctAnalysesIfAutovalidationNotTriggered(context);
        goToAutoValidationState(context, ResultLinkedEvent.class.getSimpleName());
    }

    @Override
    void onEvent(Context context, ResultAutoMatchedEvent event) {
        registerPoctAnalysesIfAutovalidationNotTriggered(context);
        goToAutoValidationState(context, ResultAutoMatchedEvent.class.getSimpleName());
    }

    @Override
    void onEvent(Context context, ResultUnlinkedEvent event) {
        registerPoctAnalysesIfAutovalidationNotTriggered(context);
        goToAutoValidationState(context, ResultUnlinkedEvent.class.getSimpleName());
    }

    private void goToAutoValidationState(Context context, String reason) {
        context.nextState(new AutoValidationState(), reason);
    }

    @Override
    void onEvent(Context context, InternalAnalysisAddedEvent event) {
        registerPoctAnalysesIfAutovalidationNotTriggered(context);
    }

    private void registerPoctAnalysesIfAutovalidationNotTriggered(Context context) {
        if (autoValidationIsNotTriggered()) {
            context.registerPOCTAnalysesForValidation();
        }
    }

    private boolean autoValidationIsNotTriggered() {
        return !this.firstResultIsKnown;
    }

    @Override
    void onEvent(Context context, InternalAnalysisRemovedEvent event) {
        if (autoValidationIsNotTriggered()) {
            context.unregisterAnalysisFromValidation(event.getAnalysisNumber());
        }
    }

    @Override
    void onEvent(Context context, OrderAutoValidatedEvent event) {
        context.nextState(new Lab400RequestState(), OrderAutoValidatedEvent.class.getSimpleName());
    }

    @Override
    void onEvent(Context context, OrderValidatedEvent event) {
        context.nextState(new Lab400RequestState(), OrderValidatedEvent.class.getSimpleName());
    }

    @Override
    void onEvent(Context context, OrderTubeValidatedEvent event) {
        if (context.hasPoctTests()) {
            context.nextState(new AutoValidationState(), OrderTubeValidatedEvent.class.getSimpleName());
        }
    }
}
