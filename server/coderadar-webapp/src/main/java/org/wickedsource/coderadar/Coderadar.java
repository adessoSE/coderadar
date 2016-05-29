package org.wickedsource.coderadar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackageClasses = {Coderadar.class})
@EnableEntityLinks
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableScheduling
@EnableTransactionManagement
public class Coderadar {

    public static void main(String[] args) {
        SpringApplication.run(Coderadar.class, args);
    }


}
