package org.springframework.restdocs.hypermedia;

import java.util.Arrays;

/**
 * Extension of the default LinksSnippet to document hypermedia links contained in a response that
 * allows extraction of links not only in the root of a JSON document but within a certain JSON path
 * of the document.
 */
public class JsonPathLinksSnippet extends LinksSnippet {

  /**
   * Constructor.
   *
   * @param jsonPath the JSON path that defines the sub node in the response JSON document which
   *     contains the links to be documented.
   * @param descriptors descriptors for all links that are expected in the sub node.
   */
  public JsonPathLinksSnippet(String jsonPath, LinkDescriptor... descriptors) {
    super(new JsonPathLinkExtractor(jsonPath), Arrays.asList(descriptors));
  }
}
