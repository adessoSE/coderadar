package org.wickedsource.coderadar.analyzer.loc.profiles;

import java.util.Optional;
import java.util.regex.Pattern;

public class XmlLocProfile implements LocProfile {

    private Pattern multiLineCommentStart = Pattern.compile("<!--");

    private Pattern multiLineCommentEnd = Pattern.compile("-->");

    private Pattern stringDelimiter = Pattern.compile("\"");

    @Override
    public Pattern multiLineCommentStart() {
        return multiLineCommentStart;
    }

    @Override
    public Pattern multiLineCommentEnd() {
        return multiLineCommentEnd;
    }

    @Override
    public Optional<Pattern> singleLineCommentStart() {
        return Optional.empty();
    }

    @Override
    public Pattern stringDelimiter() {
        return stringDelimiter;
    }

    @Override
    public Optional<Pattern> headerOrFooter() {
        return Optional.empty();
    }
}
