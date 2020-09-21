package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.AbstractInternalAnalysisEvent;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateFinalizedEvent;
import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;

import java.util.UUID;


public class InternalAnalysisAggregateFinalizedEventTest extends BaseInternalAnalysisEventTest {

    @Override
    protected AbstractInternalAnalysisEvent item() {
        return new InternalAnalysisAggregateFinalizedEvent(new InternalAnalysisAggregateId(UUID.randomUUID().toString()));
    }
}