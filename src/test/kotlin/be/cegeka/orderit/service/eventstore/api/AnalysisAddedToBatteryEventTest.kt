package be.cegeka.orderit.service.eventstore.api;

public class AnalysisAddedToBatteryEventTest extends BaseInternalAnalysisEventTest {
    
    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId(";wefnlkwemv");
        return new AnalysisAddedToBatteryEvent(aggregateIdentifier, AnalysisNumber.from(987), AnalysisNumber.from(567));
    }
}