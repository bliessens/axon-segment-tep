package be.cegeka.orderit.service.eventstore.api

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException
import java.io.Serializable

data class InternalAnalysisAggregateId @JsonCreator constructor(@field:JsonProperty("uuid") @param:JsonProperty("uuid") val uuid: String)

data class AnalysisNumber constructor(private val analysis: Long) {

    fun asInteger(): Int {
        return analysis.toInt()
    }

    companion object {
        @JvmStatic
        fun from(value: Int): AnalysisNumber {
            return AnalysisNumber(value.toLong())
        }
    }
}

class AnalysisNumberModule : SimpleModule() {
    protected class AnalysisNumberSerializer : StdSerializer<AnalysisNumber>(AnalysisNumber::class.java) {
        @Throws(IOException::class)
        override fun serialize(value: AnalysisNumber, gen: JsonGenerator, provider: SerializerProvider) {
            gen.writeNumber(value.asInteger())
        }
    }

    protected class AnalysisNumberDeserializer : StdDeserializer<AnalysisNumber?>(AnalysisNumber::class.java) {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): AnalysisNumber {
            return AnalysisNumber.from(p.valueAsInt)
        }
    }

    init {
        addSerializer(AnalysisNumber::class.java, AnalysisNumberSerializer())
        addDeserializer(AnalysisNumber::class.java, AnalysisNumberDeserializer())
    }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
sealed class AbstractInternalAnalysisEvent : Serializable {
    abstract val identifier: InternalAnalysisAggregateId
}


abstract class AbstractAnalysisReference : AbstractInternalAnalysisEvent() {
    abstract val orderId: String
    abstract val analysisNumber: AnalysisNumber
    abstract val unit: String
    abstract val user: String
}

data class InternalAnalysisAddedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                               @field:JsonProperty("orderId") @param:JsonProperty("orderId") override val orderId: String,
                                                               @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") override val analysisNumber: AnalysisNumber,
                                                               @field:JsonProperty("unit") @param:JsonProperty("unit") override val unit: String,
                                                               @field:JsonProperty("user") @param:JsonProperty("user") override val user: String) : AbstractAnalysisReference()


data class LabelOnlyAnalysisAddedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                                @field:JsonProperty("orderId") @param:JsonProperty("orderId") override val orderId: String,
                                                                @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") override val analysisNumber: AnalysisNumber,
                                                                @field:JsonProperty("unit") @param:JsonProperty("unit") override val unit: String,
                                                                @field:JsonProperty("user") @param:JsonProperty("user") override val user: String) : AbstractAnalysisReference()

data class LabelOnlyAnalysisRemovedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                                  @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                                  @field:JsonProperty("user") @param:JsonProperty("user") val user: String) : AbstractInternalAnalysisEvent()


data class AnalysisAddedToBatteryEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                                @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                                @field:JsonProperty("batteryNumber") @param:JsonProperty("batteryNumber") val batteryNumber: AnalysisNumber) : AbstractInternalAnalysisEvent()


data class InternalAnalysisRemovedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                                 @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                                 @field:JsonProperty("user") @param:JsonProperty("user") val user: String) : AbstractInternalAnalysisEvent()


data class AnalysisRemovedFromBatteryEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                                    @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                                    @field:JsonProperty("batteryNumber") @param:JsonProperty("batteryNumber") val batteryNumber: AnalysisNumber) : AbstractInternalAnalysisEvent()


data class InternalAnalysisAggregateCreatedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                                          @field:JsonProperty("orderIdentification") @param:JsonProperty("orderIdentification") val orderIdentification: String) : AbstractInternalAnalysisEvent()


data class InternalAnalysisAggregateFinalizedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId) : AbstractInternalAnalysisEvent()


data class ResultAutoMatchedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                           @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                           @field:JsonProperty("result") @param:JsonProperty("result") val result: String,
                                                           @field:JsonProperty("unit") @param:JsonProperty("unit") val unit: String?,
                                                           @field:JsonProperty("device") @param:JsonProperty("device") val device: String,
                                                           @field:JsonProperty("observationGroupId") @param:JsonProperty("observationGroupId") val observationGroupId: String,
                                                           @field:JsonProperty("user") @param:JsonProperty("user") val user: String) : AbstractInternalAnalysisEvent()


data class ResultChangedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                       @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                       @field:JsonProperty("result") @param:JsonProperty("result") val result: String,
                                                       @field:JsonProperty("user") @param:JsonProperty("user") val user: String) : AbstractInternalAnalysisEvent()


data class ResultLinkedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                      @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                      @field:JsonProperty("result") @param:JsonProperty("result") val result: String,
                                                      @field:JsonProperty("unit") @param:JsonProperty("unit") val unit: String,
                                                      @field:JsonProperty("device") @param:JsonProperty("device") val device: String,
                                                      @field:JsonProperty("observationGroupId") @param:JsonProperty("observationGroupId") val observationGroupId: String,
                                                      @field:JsonProperty("user") @param:JsonProperty("user") val user: String) : AbstractInternalAnalysisEvent()


data class ResultUnlinkedEvent @JsonCreator constructor(@field:JsonProperty("identifier") @param:JsonProperty("identifier") override val identifier: InternalAnalysisAggregateId,
                                                        @field:JsonProperty("analysisNumber") @param:JsonProperty("analysisNumber") val analysisNumber: AnalysisNumber,
                                                        @field:JsonProperty("observationGroupId") @param:JsonProperty("observationGroupId") val observationGroupId: String,
                                                        @field:JsonProperty("user") @param:JsonProperty("user") val user: String) : AbstractInternalAnalysisEvent()


