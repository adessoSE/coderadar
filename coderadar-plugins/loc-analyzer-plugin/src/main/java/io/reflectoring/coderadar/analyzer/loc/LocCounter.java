package io.reflectoring.coderadar.analyzer.loc;

import io.reflectoring.coderadar.analyzer.loc.profiles.LocProfile;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocCounter {

  public Loc count(byte[] fileContent, LocProfile profile) throws IOException {
    Loc loc = new Loc();
    LocContext context = new LocContext();
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent)));
    String line;
    while ((line = reader.readLine()) != null) {
      countLine(line, profile, loc, context);
    }
    return loc;
  }

  private void countLine(String line, LocProfile profile, Loc loc, LocContext context) {
    if (isEmptyLine(line)) {
      loc.incrementLoc();
    } else if (isCommentLine(line, profile, context)) {
      loc.incrementLoc();
      loc.incrementCloc();
    } else if (isHeaderOrFooter(line, profile)) {
      loc.incrementLoc();
      loc.incrementSloc();
    } else {
      loc.incrementLoc();
      loc.incrementSloc();
      loc.incrementEloc();
    }

    updateContext(line, profile, context);
  }

  private void updateContext(String line, LocProfile profile, LocContext context) {
    List<LineMarker> markers = new ArrayList<>();
    markers.addAll(getStringDelimiters(line, profile));
    markers.addAll(getMultiLineCommentStarts(line, profile));
    markers.addAll(getMultiLineCommentEnds(line, profile));
    markers.addAll(getSingleLineCommentStarts(line, profile));
    Collections.sort(markers);

    boolean withinString = false;
    boolean withinMultiLineComment = context.isWithinMultiLineComment();

    for (LineMarker marker : markers) {
      switch (marker.getType()) {
        case STRING_DELIMITER:
          if (!withinMultiLineComment) {
            withinString = !withinString;
          }
          break;
        case SINGLE_LINE_COMMENT_START:
          // we encountered a line comment and simply do nothing
          break;
        case MULTI_LINE_COMMENT_START:
          if (!withinString) {
            withinMultiLineComment = true;
          }
          break;
        case MULTI_LINE_COMMENT_END:
          if (!withinString) {
            withinMultiLineComment = false;
          }
          break;
      }
    }

    context.setWithinMultiLineComment(withinMultiLineComment);
  }

  private List<LineMarker> getMultiLineCommentStarts(String line, LocProfile profile) {
    return getMarkers(
        line, profile.multiLineCommentStart(), LineMarker.Type.MULTI_LINE_COMMENT_START);
  }

  private List<LineMarker> getMultiLineCommentEnds(String line, LocProfile profile) {
    return getMarkers(line, profile.multiLineCommentEnd(), LineMarker.Type.MULTI_LINE_COMMENT_END);
  }

  private List<LineMarker> getSingleLineCommentStarts(String line, LocProfile profile) {
    if (profile.singleLineCommentStart().isPresent()) {
      return getMarkers(
          line, profile.singleLineCommentStart().get(), LineMarker.Type.SINGLE_LINE_COMMENT_START);
    } else {
      return Collections.emptyList();
    }
  }

  private List<LineMarker> getStringDelimiters(String line, LocProfile profile) {
    return getMarkers(line, profile.stringDelimiter(), LineMarker.Type.STRING_DELIMITER);
  }

  private List<LineMarker> getMarkers(String line, Pattern pattern, LineMarker.Type markerType) {
    List<LineMarker> markers = new ArrayList<>();
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      markers.add(new LineMarker(matcher.start(), markerType));
    }
    return markers;
  }

  private boolean isEmptyLine(String line) {
    return "".equals(line.trim());
  }

  private boolean isHeaderOrFooter(String line, LocProfile profile) {
    if (profile.headerOrFooter().isPresent()) {
      return profile.headerOrFooter().get().matcher(line).matches();
    } else {
      return false;
    }
  }

  private boolean isCommentLine(String line, LocProfile profile, LocContext context) {
    // single line comment
    if (profile.singleLineCommentStart().isPresent()) {
      Matcher singleCommentLineMatcher =
          profile.singleLineCommentStart().get().matcher(line.trim());
      if (singleCommentLineMatcher.find()) {
        int index = singleCommentLineMatcher.start();
        // it's a comment line if it starts with the comment marker
        if (index == 0) {
          return true;
        }
      }
    }

    // multi line comment
    Matcher multiCommentLineMatcher = profile.multiLineCommentStart().matcher(line.trim());
    if (multiCommentLineMatcher.find()) {
      int index = multiCommentLineMatcher.start();
      // it's a comment line if it starts with the comment marker
      if (index == 0) {
        return true;
      }
    }

    // line within a multi line comment
    return context.isWithinMultiLineComment();
  }
}
