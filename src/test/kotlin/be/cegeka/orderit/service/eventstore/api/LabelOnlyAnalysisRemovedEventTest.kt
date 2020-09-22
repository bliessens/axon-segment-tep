package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class LabelOnlyAnalysisRemovedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId("safdghjk")
        return LabelOnlyAnalysisRemovedEvent(aggregateIdentifier, from(45), "user")
    }
}