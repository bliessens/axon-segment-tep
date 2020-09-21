package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.OrderAutoValidatedEvent;
import be.cegeka.orderit.service.eventstore.api.OrderTubeValidatedEvent;
import be.cegeka.orderit.service.eventstore.api.OrderValidatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AutoValidationState extends ContextState {

    private static final Logger LOG = LoggerFactory.getLogger(AutoValidationState.class);

    @Override
    void onStateEntry(Context context) {
        LOG.trace(getClass().getSimpleName() + ".onStateEntry() {"); // NOSONAR
        LOG.trace("\tSet flag3='Y' in ORDHDR and ORDDTL for all POCT analyses in known to this saga");
        LOG.trace("}");

        context.enableAutoValidation();
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
        context.nextState(new Lab400RequestState(), OrderTubeValidatedEvent.class.getSimpleName());
    }

}
