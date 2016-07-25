package org.wickedsource.coderadar.testframework.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.restdocs.hypermedia.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Collection of utility methods to work with JSON strings.
 */
public class JsonHelper {

    private static ObjectMapper halMapper;

    static {
        halMapper = new ObjectMapper();
        halMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        halMapper.registerModule(new Jackson2HalModule());
    }

    private JsonHelper() {

    }

    /**
     * Parses the given JSON string into a Java object using a standard Jackson mapper.
     *
     * @param json    the JSON string to parse.
     * @param toClass class of the target object.
     * @param <T>     type of the target object.
     * @return the Java object the JSON string was mapped into.
     * @throws IOException if the JSON string could not be parsed into an object of the given target type.
     */
    public static <T> T fromJson(String json, Class<T> toClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, toClass);
    }

    /**
     * Maps the given object to JSON using a standard Jackson mapper.
     */
    public static <T> String toJson(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * Maps the given object to JSON, ignoring the "links" property that contains the
     * hypermedia links. This method should be used for mapping request payload only,
     * since the hypermedia links are expected in response payloads.
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

    /**
     * Jackson Mixin used to ignore HATEOAS Links.
     */
    private abstract class IgnoreLinksMixin {
        @JsonIgnore
        abstract List<Link> getLinks();
    }

    /**
     * Parses the given JSON string into a PagedResources object using a Jackson mapper configured
     * for HAL type HATEOAS resources.
     *
     * @param json        the JSON string to parse.
     * @param contentType class of the type of the objects contained within the PagedResources wrapper.
     * @param <T>         the type of the objects contained within the PagedResources wrapper.
     * @return a PagedResources object if parsing was successful.
     * @throws IOException if the JSON string could not be parsed into an object of the given target type.
     */
    @SuppressWarnings("unchecked")
    public static <T> PagedResources<T> fromPagedResourceJson(String json, Class<T> contentType) throws IOException {
        List<T> contentItems = new ArrayList<>();
        PagedResources<Map<String, Object>> pagedResources = halMapper.readValue(json, PagedResources.class);
        for (Map<String, Object> contentItem : pagedResources.getContent()) {
            String objectJson = halMapper.writeValueAsString(contentItem);
            T object = halMapper.readValue(objectJson, contentType);
            contentItems.add(object);
        }
        return new PagedResources<T>(contentItems, pagedResources.getMetadata());
    }

}
