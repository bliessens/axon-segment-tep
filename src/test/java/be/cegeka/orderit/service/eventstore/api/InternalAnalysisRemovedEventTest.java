package be.cegeka.orderit.service.eventstore.api;

public class InternalAnalysisRemovedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId("safdghjk");
        return new InternalAnalysisRemovedEvent(aggregateIdentifier, AnalysisNumber.from(45), "user");
    }
}