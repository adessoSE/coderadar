package org.springframework.test.web.servlet.request;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * Extension of the MockHttpServletRequestBuilder provided by Spring to support multipart requests
 * via PUT.
 */
public class ExtendedMockHttpServletRequestBuilder extends MockHttpServletRequestBuilder {

  private final List<MockMultipartFile> files = new ArrayList<MockMultipartFile>();

  ExtendedMockHttpServletRequestBuilder(
      String urlTemplate, HttpMethod method, Object... urlVariables) {
    super(method, urlTemplate, urlVariables);
    super.contentType(MediaType.MULTIPART_FORM_DATA);
  }

  public ExtendedMockHttpServletRequestBuilder file(MockMultipartFile file) {
    this.files.add(file);
    return this;
  }

  /**
   * Create a new {@link MockMultipartHttpServletRequest} based on the supplied {@code
   * ServletContext} and the {@code MockMultipartFiles} added to this builder.
   */
  @Override
  protected final MockHttpServletRequest createServletRequest(ServletContext servletContext) {
    MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest(servletContext);
    for (MockMultipartFile file : this.files) {
      request.addFile(file);
    }
    return request;
  }

  /** Creates a MockHttpServletRequestBuilder for a file upload via PUT method. */
  public static ExtendedMockHttpServletRequestBuilder fileUploadPut(
      String urlTemplate, Object... urlVariables) {
    return new ExtendedMockHttpServletRequestBuilder(urlTemplate, HttpMethod.PUT, urlVariables);
  }
}
