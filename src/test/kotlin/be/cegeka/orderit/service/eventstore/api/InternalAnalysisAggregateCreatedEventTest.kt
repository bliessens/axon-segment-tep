package be.cegeka.orderit.service.eventstore.api

class InternalAnalysisAggregateCreatedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId("safdghjk")
        return InternalAnalysisAggregateCreatedEvent(aggregateIdentifier, "12345678")
    }
}