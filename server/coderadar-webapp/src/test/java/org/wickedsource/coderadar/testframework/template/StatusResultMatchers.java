package org.wickedsource.coderadar.testframework.template;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Partial copy of the Spring class with the same name that outputs more information if a ResultMatcher fails.
 */
public class StatusResultMatchers {

    public ResultMatcher is(final int status) {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                assertEquals(String.format("Unexpected HTTP status! Server response was: %s", result.getResponse().getContentAsString()), status, result.getResponse().getStatus());
            }
        };
    }

    public ResultMatcher isOk() {
        return is(200);
    }

    public ResultMatcher isBadRequest() {
        return is(400);
    }

}
