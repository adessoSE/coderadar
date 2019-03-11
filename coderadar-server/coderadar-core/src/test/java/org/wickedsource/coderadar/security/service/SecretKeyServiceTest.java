package org.wickedsource.coderadar.security.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class SecretKeyServiceTest extends IntegrationTestTemplate {

  @Autowired private SecretKeyService sut;

  @Test
  public void getSecretKey() throws Exception {
    SecretKey secretKey = sut.getSecretKey();
    assertThat(secretKey).isNotNull();
  }
}
