package org.wickedsource.coderadar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackageClasses = {TestCoderadar.class})
@EnableEntityLinks
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableTransactionManagement
public class TestCoderadar {

    public static void main(String[] args) {
        SpringApplication.run(TestCoderadar.class, args);
    }

}
