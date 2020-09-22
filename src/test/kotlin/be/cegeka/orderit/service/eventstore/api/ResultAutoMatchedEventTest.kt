package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class ResultAutoMatchedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        return ResultAutoMatchedEvent(InternalAnalysisAggregateId("sdfg"), from(95067), "+", "unit", "device", "obsGroupId", "user")
    }
}