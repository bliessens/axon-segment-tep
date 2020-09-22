package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;
import be.cegeka.orderit.service.eventstore.api.ResultChangedEvent;

public class ResultChangedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId("safdghjk");
        return new ResultChangedEvent(aggregateIdentifier, AnalysisNumber.from(45), "23%", "usertje");
    }
}