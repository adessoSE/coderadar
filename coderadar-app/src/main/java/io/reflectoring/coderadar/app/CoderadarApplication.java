package io.reflectoring.coderadar.app;

import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties
@EntityScan(basePackages = "io.reflectoring.coderadar")
@SpringBootApplication(scanBasePackages = "io.reflectoring.coderadar")
@Controller
public class CoderadarApplication implements ErrorController {

  public static void main(String[] args) {
    Locale.setDefault(Locale.US);
    SpringApplication.run(CoderadarApplication.class, args);
  }

  @GetMapping(path = "/error")
  public String error() {
    return "forward:/index.html";
  }

  // Forward unknown paths to index.html (angular)
  @Override
  public String getErrorPath() {
    return "/error";
  }
}
