package be.cegeka.orderit.service.eventstore.api

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber.Companion.from

class AnalysisRemovedFromBatteryEventTest : BaseInternalAnalysisEventTest() {

    override fun item(): AbstractInternalAnalysisEvent {
        val aggregateIdentifier = InternalAnalysisAggregateId("powemfvnk")
        return AnalysisRemovedFromBatteryEvent(aggregateIdentifier, from(123), from(567))
    }
}