package org.wickedsource.coderadar.testframework.template;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJson;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.test.web.servlet.ResultMatcher;
import org.wickedsource.coderadar.core.rest.validation.ErrorDTO;

/** Collection of ResultMatchers to be used in MockMvc's fluent API. */
public class ResultMatchers {

  private static Logger logger = LoggerFactory.getLogger(ResultMatchers.class);

  private ResultMatchers() {}

  /**
   * Tests if the response contains the JSON representation of an object of the given type.
   *
   * @param clazz the class of the expected object.
   * @param <T> the type of the expected object.
   * @return ResultMatcher that performs the test described above.
   */
  public static <T> ResultMatcher containsResource(Class<T> clazz) {
    return result -> {
      String json = result.getResponse().getContentAsString();
      try {
        T object = fromJson(json, clazz);
        assertThat(object).isNotNull();
      } catch (Exception e) {
        throw new RuntimeException(
            String.format("expected JSON representation of class %s but found '%s'", clazz, json),
            e);
      }
    };
  }

  /**
   * Tests if the response contains the JSON representation of an object in a Spring Data Page.
   *
   * @param clazz the class of the expected object.
   * @param <T> the type of the expected object.
   * @return ResultMatcher that performs the test described above.
   */
  public static <T> ResultMatcher containsPageableResource(Class<T> clazz) {
    return result -> {
      GsonJsonParser parser = new GsonJsonParser();
      Map<String, Object> jsonResponse = parser.parseMap(result.getResponse().getContentAsString());
      Assertions.assertTrue(jsonResponse.get("content") instanceof List);
      try {
        fromJson(toJson(((List) jsonResponse.get("content")).get(0)), clazz);
      } catch (IOException e) {
        Assertions.fail();
      }
      Assertions.assertNotNull((jsonResponse.get("pageable")));
    };
  }

  /**
   * Tests if the response contains the JSON representation of an object of the given type.
   *
   * @param typeReference the type of the expected object.
   * @param <T> the type of the expected object.
   * @return ResultMatcher that performs the test described above.
   */
  public static <T> ResultMatcher containsResource(TypeReference<T> typeReference) {
    return result -> {
      String json = result.getResponse().getContentAsString();
      try {
        T object = fromJson(json, typeReference);
        assertThat(object).isNotNull();
      } catch (Exception e) {
        throw new RuntimeException(
            String.format(
                "expected JSON representation of class %s but found '%s'", typeReference, json),
            e);
      }
    };
  }

  /**
   * Tests if the response contains an error object with a validation error message for the given
   * field.
   *
   * @param fieldName the name of the field for which a validation error message is expected.
   * @return ResultMatcher that performs the test described above.
   */
  public static ResultMatcher validationErrorForField(String fieldName) {
    return result -> {
      String json = result.getResponse().getContentAsString();
      try {
        ErrorDTO errors = fromJson(json, ErrorDTO.class);
        assertThat(errors.getErrorsForField(fieldName).size())
            .isGreaterThan(0)
            .as(String.format("expected at least one validation error for field %s", fieldName));
      } catch (Exception e) {
        logger.error(
            String.format(
                "expected JSON representation of ValidationErrorsDTO but found '%s'", json),
            e);
        fail(
            String.format(
                "expected JSON representation of ValidationErrorsDTO but found '%s'", json));
      }
    };
  }

  public static StatusResultMatchers status() {
    return new StatusResultMatchers();
  }
}
