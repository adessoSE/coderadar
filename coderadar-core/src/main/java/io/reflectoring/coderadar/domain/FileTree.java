package io.reflectoring.coderadar.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTree {
  private String path;
  private List<FileTree> children;
}
