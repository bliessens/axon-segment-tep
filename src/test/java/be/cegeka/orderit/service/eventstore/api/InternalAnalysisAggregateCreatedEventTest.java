package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateCreatedEvent;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;

public class InternalAnalysisAggregateCreatedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId("safdghjk");
        return new InternalAnalysisAggregateCreatedEvent(aggregateIdentifier, "12345678");
    }
}