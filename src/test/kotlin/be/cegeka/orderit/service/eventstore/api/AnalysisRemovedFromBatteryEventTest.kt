package be.cegeka.orderit.service.eventstore.api;

public class AnalysisRemovedFromBatteryEventTest extends BaseInternalAnalysisEventTest {
    
    @Override
    protected AbstractInternalAnalysisEvent item() {
        final InternalAnalysisAggregateId aggregateIdentifier = new InternalAnalysisAggregateId("powemfvnk");
        return new AnalysisRemovedFromBatteryEvent(aggregateIdentifier, AnalysisNumber.from(123), AnalysisNumber.from(567));
    }
}