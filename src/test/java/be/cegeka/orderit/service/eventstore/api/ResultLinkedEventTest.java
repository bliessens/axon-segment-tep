package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;
import be.cegeka.orderit.service.eventstore.api.ResultLinkedEvent;

public class ResultLinkedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        return new ResultLinkedEvent(new InternalAnalysisAggregateId("oidentifoir"), AnalysisNumber.from(123), "77", "unit", "device", "observationGroupId", "user");
    }
}