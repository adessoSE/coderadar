package io.reflectoring.coderadar.rest.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackageClasses = {CoderadarTestApplication.class})
@EnableTransactionManagement(proxyTargetClass = true)
public class CoderadarTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(CoderadarTestApplication.class, args);
  }
}
