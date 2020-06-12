package io.reflectoring.coderadar.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonListWrapper<T> {
  @NotEmpty private List<T> elements;
}
