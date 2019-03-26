package org.wickedsource.coderadar.analyzer.loc.profiles;

import java.util.Optional;
import java.util.regex.Pattern;

public class JavaLocProfile implements LocProfile {

    private Pattern multiLineCommentStart = Pattern.compile("/\\*");

    private Pattern multiLineCommentEnd = Pattern.compile("\\*/");

    private Pattern singleLineCommentStart = Pattern.compile("//");

    private Pattern stringDelimiter = Pattern.compile("\"");

    private Pattern headerOrFooterLine =
            Pattern.compile("(^\\s*import.*$)|(^\\s*\\{\\s*$)|(^\\s*\\}\\s*$)");

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
        return Optional.of(singleLineCommentStart);
    }

    @Override
    public Pattern stringDelimiter() {
        return stringDelimiter;
    }

    @Override
    public Optional<Pattern> headerOrFooter() {
        return Optional.of(headerOrFooterLine);
    }
}
