package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class AnalysisAddedToBatteryEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId(";wefnlkwemv")
        return AnalysisAddedToBatteryEvent(aggregateIdentifier, from(987), from(567))
    }
}