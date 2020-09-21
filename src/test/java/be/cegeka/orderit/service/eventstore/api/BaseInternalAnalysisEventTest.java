package be.cegeka.orderit.service.eventstore.api;

abstract class BaseInternalAnalysisEventTest extends BaseEventSerializableTest<AbstractInternalAnalysisEvent, InternalAnalysisAggregateId> {

    @Override
    protected abstract AbstractInternalAnalysisEvent item();

    @Override
    protected Class<InternalAnalysisAggregateId> identifierType() {
        return InternalAnalysisAggregateId.class;
    }
}
