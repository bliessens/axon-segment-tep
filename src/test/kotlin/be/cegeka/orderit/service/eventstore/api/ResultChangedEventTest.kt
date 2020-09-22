package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class ResultChangedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId("safdghjk")
        return ResultChangedEvent(aggregateIdentifier, from(45), "23%", "usertje")
    }
}