package be.cegeka.orderit.service.eventstore.api

import java.util.*

class InternalAnalysisAggregateFinalizedEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        return InternalAnalysisAggregateFinalizedEvent(InternalAnalysisAggregateId(UUID.randomUUID().toString()))
    }
}