package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.OrderConfirmedEvent;

class InitializedState extends ContextState {

    @Override
    void onEvent(Context context, OrderConfirmedEvent event) {
        context.nextState(new OrderConfirmedState(context.isResultPresent()), OrderConfirmedEvent.class.getSimpleName());
    }


}
