package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXParseException;

public class FindbugsReportXsdValidatorTest extends TestReportAccessor {

  @Test
  public void testValidate() throws Exception {
    byte[] report = getValidReport();
    FindbugsReportXsdValidator validator = new FindbugsReportXsdValidator();
    List<SAXParseException> validationErrors =
        validator.validate(report, FindbugsReportXsdValidator.ValidationLevel.FATAL);
    Assert.assertEquals(0, validationErrors.size());
  }

  @Test
  public void testValidationError() throws Exception {
    byte[] report = getInvalidReport();
    FindbugsReportXsdValidator validator = new FindbugsReportXsdValidator();
    List<SAXParseException> validationErrors =
        validator.validate(report, FindbugsReportXsdValidator.ValidationLevel.ERROR);
    Assert.assertTrue(validationErrors.size() > 0);
  }
}
