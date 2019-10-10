package io.reflectoring.coderadar.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "io.reflectoring.coderadar")
@EntityScan(basePackages = "io.reflectoring.coderadar")
public class CoderadarTestApplication {

  @Bean
  public AsyncListenableTaskExecutor taskExecutor(){
    return new ConcurrentTaskExecutor(Runnable::run);
  }

  public static void main(String[] args) {
    SpringApplication.run(CoderadarTestApplication.class, args);
  }
}
