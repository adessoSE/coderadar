package io.reflectoring.coderadar.rest.integration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.restdocs.hypermedia.Link;

/** Collection of utility methods to work with JSON strings. */
public class JsonHelper {

    private static ObjectMapper halMapper;

    static {
        halMapper = new ObjectMapper();
        halMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonHelper() {}

    /**
     * Parses the given JSON string into a Java object using a standard Jackson mapper.
     *
     * @param json the JSON string to parse.
     * @param toClass class of the target object.
     * @param <T> type of the target object.
     * @return the Java object the JSON string was mapped into.
     * @throws IOException if the JSON string could not be parsed into an object of the given target
     *     type.
     */
    public static <T> T fromJson(String json, Class<T> toClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, toClass);
    }

    /**
     * Parses the given JSON string into a Java object using a standard Jackson mapper. This method
     * allows to specify a {@link TypeReference} that describes the class to parse and thus supports
     * parsing generic types.
     *
     * @param json the JSON string to parse.
     * @param typeReference a {@link TypeReference} object that describes the class to parse.
     * @param <T> type of the target object.
     * @return the Java object the JSON string was mapped into.
     * @throws IOException if the JSON string could not be parsed into an object of the given target
     *     type.
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, typeReference);
    }

    /** Maps the given object to JSON using a standard Jackson mapper. */
    public static <T> String toJson(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * Maps the given object to JSON, ignoring the "links" property that contains the hypermedia
     * links. This method should be used for mapping request payload only, since the hypermedia links
     * are expected in response payloads.
     */
    public static <T> String toJsonWithoutLinks(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixIn(object.getClass(), IgnoreLinksMixin.class);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /** Jackson Mixin used to ignore HATEOAS Links. */
    private abstract class IgnoreLinksMixin {
        @JsonIgnore
        abstract List<Link> getLinks();
    }
}
