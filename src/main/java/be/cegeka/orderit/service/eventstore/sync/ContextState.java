package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.*;

import java.io.Serializable;

abstract class ContextState implements Serializable {

    void onStateEntry(Context context) {
    }

    void onEvent(Context context, InternalAnalysisAddedEvent event) {
    }

    void onEvent(Context context, InternalAnalysisRemovedEvent event) {
    }

    void onEvent(Context context, ResultChangedEvent event) {
    }

    void onEvent(Context context, OrderConfirmedEvent event) {
    }

    void onEvent(Context context, OrderAutoValidatedEvent event) {
    }

    void onEvent(Context context, OrderTubeValidatedEvent event) {
    }

    void onEvent(Context context, OrderValidatedEvent event) {
    }

    void onEvent(Context context, ResultUnlinkedEvent event) {
    }

    void onEvent(Context context, ResultLinkedEvent event) {
    }

    void onEvent(Context context, ResultAutoMatchedEvent event) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
