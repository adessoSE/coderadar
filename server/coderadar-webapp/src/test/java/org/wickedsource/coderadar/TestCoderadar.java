package org.wickedsource.coderadar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EntityScan(basePackageClasses = {TestCoderadar.class})
@EnableEntityLinks
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class TestCoderadar {

    public static void main(String[] args) {
        SpringApplication.run(Coderadar.class, args);
    }

}
