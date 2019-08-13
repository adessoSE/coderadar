package io.reflectoring.coderadar.rest.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.ResultMatcher;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}
