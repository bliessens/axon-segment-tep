package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class LabelOnlyAnalysisAddedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        return LabelOnlyAnalysisAddedEvent(InternalAnalysisAggregateId("sadfgh"), "orderId", from(24536), "unit", "user")
    }
}