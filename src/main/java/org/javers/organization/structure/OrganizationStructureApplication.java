package org.javers.organization.structure;

import org.javers.spring.boot.mongo.JaversMongoAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = JaversMongoAutoConfiguration.class)
public class OrganizationStructureApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationStructureApplication.class, args);
    }
}
