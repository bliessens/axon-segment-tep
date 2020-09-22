package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAddedEvent;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;

public class InternalAnalysisAddedEventTest extends BaseInternalAnalysisEventTest {
    
    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId("safdghjk");
        return new InternalAnalysisAddedEvent(aggregateIdentifier, "12345678", AnalysisNumber.from(45), "ml", "user");
    }
}