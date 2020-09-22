package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class ResultLinkedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        return ResultLinkedEvent(InternalAnalysisAggregateId("oidentifoir"), from(123), "77", "unit", "device", "observationGroupId", "user")
    }
}