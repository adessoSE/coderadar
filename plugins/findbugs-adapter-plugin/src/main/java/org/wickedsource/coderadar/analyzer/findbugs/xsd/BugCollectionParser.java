package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.xml.sax.SAXParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.util.List;

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

    public void validate(byte[] bugCollection) throws AnalyzerException {
        FindbugsReportXsdValidator validator = new FindbugsReportXsdValidator();
        List<SAXParseException> validationErrors = validator.validate(bugCollection, FindbugsReportXsdValidator.ValidationLevel.FATAL);
        if (!validationErrors.isEmpty()) {
            throw new AnalyzerException("Not a valid Findbugs report! First XSD validation error:", validationErrors.get(0));
        }
    }
}
