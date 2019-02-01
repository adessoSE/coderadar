package org.springframework.restdocs.hypermedia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

/**
* Documentation snippet that doesn't document ALL fields of a response but only a part of the
* response that matches a certain JSON path string.
*/
public class JsonPathResponseFieldsSnippet extends AbstractFieldsSnippet {

	private final String jsonPath;

	/**
	* @param jsonPath the JSON path that defines the section of the response JSON structure to
	*     document.
	* @param descriptors descriptors of the fields that are to be documented.
	*/
	public JsonPathResponseFieldsSnippet(String jsonPath, FieldDescriptor... descriptors) {
		super("response", Arrays.asList(descriptors), null, false);
		this.jsonPath = jsonPath;
	}

	@Override
	protected MediaType getContentType(Operation operation) {
		return operation.getRequest().getHeaders().getContentType();
	}

	@Override
	protected byte[] getContent(Operation operation) throws IOException {
		String json = operation.getResponse().getContentAsString();
		LinkedHashMap<String, Object> node = JsonPath.read(json, this.jsonPath);
		node.remove("_links"); // ignoring HAL-style hypermedia links
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(node).getBytes();
	}

	public String getJsonPath() {
		return jsonPath;
	}
}
