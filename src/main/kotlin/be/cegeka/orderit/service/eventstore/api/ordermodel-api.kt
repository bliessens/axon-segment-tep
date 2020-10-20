package be.cegeka.orderit.service.eventstore.api

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.axonframework.serialization.Revision
import java.io.Serializable
import java.util.*

data class OrderModelAggregateId @JsonCreator constructor(@field:JsonProperty("uuid") val uuid: String) : Serializable {

    constructor(uuid: UUID) : this(uuid.toString())

}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
sealed class AbstractOrderModelEvent : Serializable {
    abstract val identifier: OrderModelAggregateId
}

abstract class AbstractOrderModelValidationEvent() : AbstractOrderModelEvent() {
    abstract val institute: String
    abstract val orderId: String
}


@Revision("2.0")
data class OrderAutoValidatedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: OrderModelAggregateId,
                                                            @field:JsonProperty("institute") @param:JsonProperty("institute") override val institute: String,
                                                            @field:JsonProperty("requestId") @param:JsonProperty("requestId") override val orderId: String) : AbstractOrderModelValidationEvent()

@Revision("2.0")
data class OrderTubeValidatedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: OrderModelAggregateId,
                                                            @field:JsonProperty("institute") @param:JsonProperty("institute") override val institute: String,
                                                            @field:JsonProperty("requestId") @param:JsonProperty("requestId") override val orderId: String) : AbstractOrderModelValidationEvent()

@Revision("2.0")
data class OrderValidatedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: OrderModelAggregateId,
                                                        @field:JsonProperty("institute") @param:JsonProperty("institute") override val institute: String,
                                                        @field:JsonProperty("requestId") @param:JsonProperty("requestId") override val orderId: String) : AbstractOrderModelValidationEvent()

data class OrderConfirmedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: OrderModelAggregateId,
                                                        @field:JsonProperty("institute") @param:JsonProperty("institute") val institute: String,
                                                        @field:JsonProperty("requestId") @param:JsonProperty("requestId") val orderId: String) : AbstractOrderModelEvent()


data class OrderFinalizedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: OrderModelAggregateId,
                                                        @field:JsonProperty("requestId") @param:JsonProperty("requestId") val orderId: String) : AbstractOrderModelEvent()

