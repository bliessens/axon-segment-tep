package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class InternalAnalysisRemovedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId("safdghjk")
        return InternalAnalysisRemovedEvent(aggregateIdentifier, from(45), "user")
    }
}