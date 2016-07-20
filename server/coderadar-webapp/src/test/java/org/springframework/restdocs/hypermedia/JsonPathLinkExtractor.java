package org.springframework.restdocs.hypermedia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.restdocs.operation.OperationResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Extractor that extracts HAL-type links from a sub-node of a JSON document
 * identified by a JSON path.
 */
public class JsonPathLinkExtractor extends HalLinkExtractor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String jsonPath;

    /**
     * Constructor.
     * @param jsonPath the JSON path to the sub-node whose "_links" attribute contains
     *                 the links that are to be extracted.
     */
    public JsonPathLinkExtractor(String jsonPath){
        this.jsonPath = jsonPath;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List<Link>> extractLinks(OperationResponse response)
            throws IOException {
        Map<String, Object> jsonContent = this.objectMapper
                .readValue(extractPath(response, this.jsonPath), Map.class);
        return extractLinks(jsonContent);
    }

    private String extractPath(OperationResponse response, String jsonPath) throws JsonProcessingException {
        String json = response.getContentAsString();
        LinkedHashMap<String, Object> node = JsonPath.read(json, jsonPath);
        return objectMapper.writeValueAsString(node);
    }

}
