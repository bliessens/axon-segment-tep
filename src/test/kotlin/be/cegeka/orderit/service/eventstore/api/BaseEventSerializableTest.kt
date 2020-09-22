package be.cegeka.orderit.service.eventstore.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.reflections.ReflectionUtils
import java.io.IOException
import java.lang.reflect.Field
import java.util.function.Predicate

/**
 * Parent test class for unit testing domain objects.
 *
 *
 *
 *
 * Main purpose is to test JSON (de)serialization of objects.
 *
 * @param <ITEM_TYPE>
 * @param <AGGREGATE_ID_TYPE>
</AGGREGATE_ID_TYPE></ITEM_TYPE> */
abstract class BaseEventSerializableTest<ITEM_TYPE : Any, AGGREGATE_ID_TYPE> {

    private val mapper = jacksonObjectMapper()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mapper.registerModule(JavaTimeModule())
        mapper.registerModule(AnalysisNumberModule())
    }

    @Throws(IOException::class)
    @Test
    fun isSerializableWithJackson(): Unit {
        assertSerializable(item())
    }

    protected abstract fun item(): ITEM_TYPE

    protected abstract fun identifierType(): Class<AGGREGATE_ID_TYPE>

    @Throws(IOException::class)
    private fun assertSerializable(instance: Any) {
        val actual = readValue(writeValue(instance), instance.javaClass)
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(instance)
    }

    @Throws(JsonProcessingException::class)
    protected fun writeValue(value: Any?): String {
        return mapper.writeValueAsString(value)
    }

    @Throws(IOException::class)
    protected fun <T> readValue(json: String?, targetClass: Class<T>?): T {
        return mapper.readValue(json, targetClass)
    }

    @Test
    @Throws(Exception::class)
    fun aggregateIdentifierOfItemIsSerializedAs_identifier() {
        val itemType: Class<*> = item()::class.java
        var success = false
        for (field in ReflectionUtils.getAllFields(itemType, Predicate { field: Field -> field.getAnnotationsByType(JsonProperty::class.java).size > 0 })) {
            if (field.type == identifierType()) {
                val jsonProp = field.getAnnotation(JsonProperty::class.java)
                assertThat(jsonProp.value).isEqualTo("identifier")
                success = true
            }
        }
        assertThat(success).isTrue
    }

    @Test
    fun parseJsonFile() {
        val resource = item()::class.simpleName + ".json"
        ClassLoader.getSystemResourceAsStream(resource)?.let { it ->
            println("Unmarshalling file from 'classpath:/${resource}.json")

            val value = mapper.readValue(it, item()::class.java)

            assertThat(value).isNotNull
        }
    }
}