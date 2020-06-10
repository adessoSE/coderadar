package io.reflectoring.coderadar.rest;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonListWrapper<T> {
  @NotEmpty private List<T> elements;
}
