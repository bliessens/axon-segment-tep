package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class InternalAnalysisAddedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId("safdghjk")
        return InternalAnalysisAddedEvent(aggregateIdentifier, "12345678", from(45), "ml", "user")
    }
}