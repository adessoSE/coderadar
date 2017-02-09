package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.io.ByteArrayInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BugCollectionParser {

  /**
   * Creates a BugCollection instance from the given xml byte array.
   *
   * @param bugCollection the byte array to be interpreted as xml.
   * @return a BugCollection instance if the xml could be parsed successfully.
   * @throws JAXBException if the byte array could not be parsed as xml.
   */
  public BugCollection fromBytes(byte[] bugCollection) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(BugCollection.class);
    Unmarshaller parser = context.createUnmarshaller();
    return (BugCollection) parser.unmarshal(new ByteArrayInputStream(bugCollection));
  }
}
