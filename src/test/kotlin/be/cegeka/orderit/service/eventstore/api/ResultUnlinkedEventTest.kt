package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;
import be.cegeka.orderit.service.eventstore.api.ResultUnlinkedEvent;

public class ResultUnlinkedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        return new ResultUnlinkedEvent(new InternalAnalysisAggregateId("ajdentifaajer"), AnalysisNumber.from(987), "observationGroupId", "user");
    }
}