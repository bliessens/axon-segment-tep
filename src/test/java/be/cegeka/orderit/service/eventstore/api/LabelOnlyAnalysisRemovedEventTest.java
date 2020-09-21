package be.cegeka.orderit.service.eventstore.api;

public class LabelOnlyAnalysisRemovedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId("safdghjk");
        return new LabelOnlyAnalysisRemovedEvent(aggregateIdentifier, AnalysisNumber.from(45), "user");
    }
}