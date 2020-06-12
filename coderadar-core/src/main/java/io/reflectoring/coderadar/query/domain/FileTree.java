package io.reflectoring.coderadar.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTree {
  private String path;
  private List<FileTree> children;
}
