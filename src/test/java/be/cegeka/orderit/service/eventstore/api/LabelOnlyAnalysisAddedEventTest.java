package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;
import be.cegeka.orderit.service.eventstore.api.LabelOnlyAnalysisAddedEvent;

public class LabelOnlyAnalysisAddedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        return new LabelOnlyAnalysisAddedEvent(new InternalAnalysisAggregateId("sadfgh"), "orderId", AnalysisNumber.from(24536), "unit", "user");
    }

}