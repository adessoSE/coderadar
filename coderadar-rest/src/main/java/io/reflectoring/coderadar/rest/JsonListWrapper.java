package io.reflectoring.coderadar.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonListWrapper<T> {
  private List<T> elements;
}
