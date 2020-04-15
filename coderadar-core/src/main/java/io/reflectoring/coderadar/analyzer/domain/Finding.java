package io.reflectoring.coderadar.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A finding represent line coordinates in a single file for a given metric. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Finding {
  private Integer lineStart;
  private Integer lineEnd;
  private Integer charStart;
  private Integer charEnd;
}
