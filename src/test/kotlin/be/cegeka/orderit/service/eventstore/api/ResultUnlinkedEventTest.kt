package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class ResultUnlinkedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        return ResultUnlinkedEvent(InternalAnalysisAggregateId("ajdentifaajer"), from(987), "observationGroupId", "user")
    }
}