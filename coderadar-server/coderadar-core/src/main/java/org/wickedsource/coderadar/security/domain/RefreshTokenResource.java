package org.wickedsource.coderadar.security.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenResource {

  @NotNull private String accessToken;

  @NotNull private String refreshToken;
}
