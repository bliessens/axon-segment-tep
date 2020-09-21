package be.cegeka.orderit.service.eventstore.api;

public class ResultAutoMatchedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        return new ResultAutoMatchedEvent(new InternalAnalysisAggregateId("sdfg"), AnalysisNumber.from(95067), "+", "unit", "device", "obsGroupId", "user");
    }
}