package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LAB400 requests exists for the OrderModel ( either through normal recipient Validation, either through Auto Validation)
 * <p>
 * in this state, aggregate must be kept in sync with LAB400
 */
class Lab400RequestState extends ContextState {

    private static final Logger LOG = LoggerFactory.getLogger(Lab400RequestState.class);

    @Override
    void onStateEntry(Context context) {
        //enable auto validation here
        context.enableAutoValidation();

        context.transmitResultValues();
        LOG.trace(getClass().getSimpleName() + ".onStateEntry() {"); // NOSONAR
        LOG.trace("\tCall ANLMANIP + GLPSTORE2 for current values");
        LOG.trace("}");
    }

    @Override
    void onEvent(Context context, InternalAnalysisRemovedEvent event) {
        context.removeAnalysis();
        LOG.trace("Remove analysis from LAB400");
    }

    @Override
    void onEvent(Context context, InternalAnalysisAddedEvent event) {
        context.transmitAnalyses();
        LOG.trace(getClass().getSimpleName() + ".onEvent(" + event.getClass().getSimpleName() + " ) {"); // NOSONAR
        LOG.trace("\tCall ANLMANIP to add given POCT analysis (without result) to Lab400 request");
        LOG.trace("}");
    }

    @Override
    void onEvent(Context context, ResultChangedEvent event) {
        triggerTransmitValues(context, event.getClass().getSimpleName());
    }

    @Override
    void onEvent(Context context, ResultLinkedEvent event) {
        triggerTransmitValues(context, event.getClass().getSimpleName());
    }

    @Override
    void onEvent(Context context, ResultAutoMatchedEvent event) {
        triggerTransmitValues(context, event.getClass().getSimpleName());
    }

    @Override
    void onEvent(Context context, ResultUnlinkedEvent event) {
        triggerTransmitValues(context, event.getClass().getSimpleName());
    }

    private void triggerTransmitValues(Context context, String event) {
        context.transmitResultValues();
        LOG.trace(getClass().getSimpleName() + ".onEvent(" + event + " ) {"); // NOSONAR

        LOG.trace("\tCall ANLMANIP to add given POCT analysis (with result) to Lab400 request");
        LOG.trace("\tCall GLPSTORE2 with given POCT analysis and result");
        LOG.trace("}");
    }

}
