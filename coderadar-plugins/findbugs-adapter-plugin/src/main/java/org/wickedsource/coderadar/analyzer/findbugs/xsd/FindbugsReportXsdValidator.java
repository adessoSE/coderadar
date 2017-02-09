package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class FindbugsReportXsdValidator {

  public enum ValidationLevel {
    FATAL,

    ERROR,

    WARNING;
  }

  /**
   * Validates that the given byte array is a valid report XML file of findbugs.
   *
   * @param findbugsReport the byte array to validate.
   * @param validationLevel the level of validation errors to report. The result list will only
   *     contain validation errors of this level or the levels above (i.e. if you pass level ERROR,
   *     the result list will contain ERRORs and FATALs).
   * @return list of validation errors. Validation is successful if this list is empty.
   */
  public List<SAXParseException> validate(
      byte[] findbugsReport, final ValidationLevel validationLevel) {
    ByteArrayInputStream in = new ByteArrayInputStream(findbugsReport);
    return validate(in, validationLevel);
  }

  /**
   * Validates that the given InputStream is a valid report XML file of findbugs.
   *
   * @param findbugsReport the InputStream to validate.
   * @param validationLevel the level of validation errors to report. The result list will only
   *     contain validation errors of this level or the levels above (i.e. if you pass level ERROR,
   *     the result list will contain ERRORs and FATALs).
   * @return list of validation errors. Validation is successful if this list is empty.
   */
  public List<SAXParseException> validate(
      InputStream findbugsReport, final ValidationLevel validationLevel) {
    try {

      if (findbugsReport == null) {
        throw new IllegalArgumentException("InputStream is NULL!");
      }

      InputStream xsdInputStream = getClass().getResourceAsStream("/xsd/bugcollection.xsd");
      if (xsdInputStream == null) {
        throw new IllegalStateException(
            "Could not find XSD file in classpath (bugcollection.xsd)!");
      }
      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = schemaFactory.newSchema(new StreamSource(xsdInputStream));

      final List<SAXParseException> validationErrors = new ArrayList<>();
      Validator validator = schema.newValidator();
      validator.setErrorHandler(
          new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
              if (validationLevel == ValidationLevel.WARNING) {
                validationErrors.add(exception);
              }
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
              if (validationLevel == ValidationLevel.WARNING
                  || validationLevel == ValidationLevel.ERROR) {
                validationErrors.add(exception);
              }
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
              validationErrors.add(exception);
            }
          });
      validator.validate(new StreamSource(findbugsReport));
      return validationErrors;
    } catch (SAXException | IOException e) {
      throw new IllegalStateException("Error while validating xml file against xsd!", e);
    }
  }
}
