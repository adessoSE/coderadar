package org.wickedsource.coderadar.testframework.template;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * Partial copy of the Spring class with the same name that outputs more information if a
 * ResultMatcher fails.
 */
public class StatusResultMatchers {

  public ResultMatcher is(final int status) {
    return result ->
        assertEquals(
            String.format(
                "Unexpected HTTP status! Server response was: %s",
                result.getResponse().getContentAsString()),
            status,
            result.getResponse().getStatus());
  }

  public ResultMatcher isOk() {
    return is(HttpStatus.OK.value());
  }

  public ResultMatcher isBadRequest() {
    return is(HttpStatus.BAD_REQUEST.value());
  }

  public ResultMatcher isConflict() {
    return is(HttpStatus.CONFLICT.value());
  }

  public ResultMatcher isCreated() {
    return is(HttpStatus.CREATED.value());
  }

  public ResultMatcher isNotFound() {
    return is(HttpStatus.NOT_FOUND.value());
  }

  public ResultMatcher isForbidden() {
    return is(HttpStatus.FORBIDDEN.value());
  }
}
