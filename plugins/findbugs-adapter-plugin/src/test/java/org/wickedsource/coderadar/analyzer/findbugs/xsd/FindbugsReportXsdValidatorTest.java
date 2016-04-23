package org.wickedsource.coderadar.analyzer.findbugs.xsd;


import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import java.io.InputStream;
import java.util.List;

public class FindbugsReportXsdValidatorTest {

    @Test
    public void testValidate() throws Exception {
        InputStream findbugsReport = getClass().getResourceAsStream("valid-findbugs-report.xml");
        if(findbugsReport == null){
            throw new IllegalStateException("XML not found!");
        }
        FindbugsReportXsdValidator validator = new FindbugsReportXsdValidator();
        List<SAXParseException> validationErrors = validator.validate(findbugsReport, FindbugsReportXsdValidator.ValidationLevel.FATAL);
        Assert.assertEquals(0, validationErrors.size());
    }

    @Test
    public void testValidationError() throws Exception {
        InputStream findbugsReport = getClass().getResourceAsStream("invalid-findbugs-report.xml");
        if(findbugsReport == null){
            throw new IllegalStateException("XML not found!");
        }
        FindbugsReportXsdValidator validator = new FindbugsReportXsdValidator();
        List<SAXParseException> validationErrors = validator.validate(findbugsReport, FindbugsReportXsdValidator.ValidationLevel.ERROR);
        Assert.assertTrue(validationErrors.size() > 0);
    }
}