package be.cegeka.orderit.service.eventstore.api

abstract class BaseInternalAnalysisEventTest : BaseEventSerializableTest<AbstractInternalAnalysisEvent, InternalAnalysisAggregateId>() {

    abstract override fun item(): AbstractInternalAnalysisEvent

    override fun identifierType(): Class<InternalAnalysisAggregateId> {
        return InternalAnalysisAggregateId::class.java
    }
}