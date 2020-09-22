package be.cegeka.orderit.service.eventstore.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.reflections.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Parent test class for unit testing domain objects. <p/>
 * <p>
 * Main purpose is to test JSON (de)serialization of objects.
 *
 * @param <ITEM_TYPE>
 * @param <AGGREGATE_ID_TYPE>
 */
public abstract class BaseEventSerializableTest<ITEM_TYPE, AGGREGATE_ID_TYPE> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new AnalysisNumberModule());
    }

    @Test
    public void isSerializableWithJackson() throws IOException {
        assertSerializable(item());
    }

    protected abstract ITEM_TYPE item();

    protected abstract Class<AGGREGATE_ID_TYPE> identifierType();

    private void assertSerializable(Object object) throws IOException {
        Object actual = readValue(writeValue(object), object.getClass());

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(object);
    }

    protected String writeValue(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    protected <T> T readValue(String json, Class<T> targetClass) throws IOException {
        return mapper.readValue(json, targetClass);
    }

    @Test
    public void aggregateIdentifierOfItemIsSerializedAs_identifier() throws Exception {
        Class itemType = item().getClass();

        boolean success = false;
        for (Field field : ReflectionUtils.getAllFields(itemType, field -> field.getAnnotationsByType(JsonProperty.class).length > 0)) {
            if (field.getType().equals(identifierType())) {
                final JsonProperty jsonProp = field.getAnnotation(JsonProperty.class);
                assertThat(jsonProp.value()).isEqualTo("identifier");
                success = true;
            }
        }
        assertThat(success).isTrue();
    }

    @Test
    public void parseJsonFile() throws IOException {
        String resource = item().getClass().getSimpleName() + ".json";
        InputStream stream = ClassLoader.getSystemResourceAsStream(resource);
        if (stream != null) {
            System.out.println(String.format("Unmarshalling file from 'classpath:/%s.json", resource));

            Object value = mapper.readValue(stream, item().getClass());

            assertThat(value).isNotNull();
        }
    }

}
