package io.reflectoring.coderadar.analyzer.loc.profiles;

import java.util.Optional;
import java.util.regex.Pattern;

/** Profile for counting lines of code in a specific programming language. */
public interface LocProfile {

  /**
   * Must return a regex Pattern that matches the start token of a multi line comment. Must NOT
   * match a complete line of code!
   */
  Pattern multiLineCommentStart();

  /**
   * Must return a regex Pattern that matches the end token of a multi line comment.# Must NOT match
   * a complete line of code!
   */
  Pattern multiLineCommentEnd();

  /**
   * If the programming language supports single line comments, this method must return a regex
   * Pattern that matches the start token of a single line comment. Otherwise may return an empty
   * Optional. Must NOT match a complete line of code!
   */
  Optional<Pattern> singleLineCommentStart();

  /**
   * Must return a regex Pattern that matches the token that acts as a delimiter for strings in the
   * programming language. Must NOT match a complete line of code!
   */
  Pattern stringDelimiter();

  /**
   * May return a regex Pattern that matches a complete line if that line is to be treated as a
   * "header" or "footer" line that should not be counted as an effective line of code. Header or
   * footer lines are lines like imports or single braces. If the programming language does not have
   * any such lines, may return an empty Optional.
   */
  Optional<Pattern> headerOrFooter();
}
