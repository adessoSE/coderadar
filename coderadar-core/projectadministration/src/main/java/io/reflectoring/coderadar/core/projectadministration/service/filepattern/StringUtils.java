package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The methods of this class are copied from the StringUtils class of the spring framework to avoid
 * the full dependency to the spring framework. Big thanks to the Spring team!
 */
public class StringUtils {

  /**
   * Tokenize the given {@code String} into a {@code String} array via a {@link StringTokenizer}.
   *
   * <p>The given {@code delimiters} string can consist of any number of delimiter characters. Each
   * of those characters can be used to separate tokens. A delimiter is always a single character;
   * for multi-character delimiters, consider using {@link #delimitedListToStringArray}.
   *
   * @param str the {@code String} to tokenize
   * @param delimiters the delimiter characters, assembled as a {@code String} (each of the
   *     characters is individually considered as a delimiter)
   * @param trimTokens trim the tokens via {@link String#trim()}
   * @param ignoreEmptyTokens omit empty tokens from the result array (only applies to tokens that
   *     are empty after trimming; StringTokenizer will not consider subsequent delimiters as token
   *     in the first place).
   * @return an array of the tokens ({@code null} if the input {@code String} was {@code null})
   * @see StringTokenizer
   * @see String#trim()
   */
  public static String[] tokenizeToStringArray(
      String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

    if (str == null) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(str, delimiters);
    List<String> tokens = new ArrayList<String>();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (trimTokens) {
        token = token.trim();
      }
      if (!ignoreEmptyTokens || token.length() > 0) {
        tokens.add(token);
      }
    }
    return toStringArray(tokens);
  }

  /**
   * Copy the given {@code Collection} into a {@code String} array.
   *
   * <p>The {@code Collection} must contain {@code String} elements only.
   *
   * @param collection the {@code Collection} to copy
   * @return the {@code String} array ({@code null} if the supplied {@code Collection} was {@code
   *     null})
   */
  public static String[] toStringArray(Collection<String> collection) {
    if (collection == null) {
      return null;
    }
    return collection.toArray(new String[collection.size()]);
  }

  /**
   * Check whether the given {@code CharSequence} contains actual <em>text</em>.
   *
   * <p>More specifically, this method returns {@code true} if the {@code CharSequence} is not
   * {@code null}, its length is greater than 0, and it contains at least one non-whitespace
   * character.
   *
   * <p>
   *
   * <pre class="code">
   * StringUtils.hasText(null) = false
   * StringUtils.hasText("") = false
   * StringUtils.hasText(" ") = false
   * StringUtils.hasText("12345") = true
   * StringUtils.hasText(" 12345 ") = true
   * </pre>
   *
   * @param str the {@code CharSequence} to check (may be {@code null})
   * @return {@code true} if the {@code CharSequence} is not {@code null}, its length is greater
   *     than 0, and it does not contain whitespace only
   * @see Character#isWhitespace
   */
  public static boolean hasText(CharSequence str) {
    if (!hasLength(str)) {
      return false;
    }
    int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check that the given {@code String} is neither {@code null} nor of length 0.
   *
   * <p>Note: this method returns {@code true} for a {@code String} that purely consists of
   * whitespace.
   *
   * @param str the {@code String} to check (may be {@code null})
   * @return {@code true} if the {@code String} is not {@code null} and has length
   * @see #hasLength(CharSequence)
   */
  public static boolean hasLength(String str) {
    return hasLength((CharSequence) str);
  }

  /**
   * Check that the given {@code CharSequence} is neither {@code null} nor of length 0.
   *
   * <p>Note: this method returns {@code true} for a {@code CharSequence} that purely consists of
   * whitespace.
   *
   * <p>
   *
   * <pre class="code">
   * StringUtils.hasLength(null) = false
   * StringUtils.hasLength("") = false
   * StringUtils.hasLength(" ") = true
   * StringUtils.hasLength("Hello") = true
   * </pre>
   *
   * @param str the {@code CharSequence} to check (may be {@code null})
   * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
   */
  public static boolean hasLength(CharSequence str) {
    return (str != null && str.length() > 0);
  }
}
