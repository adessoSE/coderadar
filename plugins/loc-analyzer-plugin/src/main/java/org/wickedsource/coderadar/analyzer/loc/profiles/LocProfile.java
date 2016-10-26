package org.wickedsource.coderadar.analyzer.loc.profiles;

import java.util.regex.Pattern;

public interface LocProfile {

    Pattern multiLineCommentStart();

    Pattern multiLineCommentEnd();

    Pattern singleLineCommentStart();

    Pattern stringDelimiter();

    Pattern headerOrFooter();

}
