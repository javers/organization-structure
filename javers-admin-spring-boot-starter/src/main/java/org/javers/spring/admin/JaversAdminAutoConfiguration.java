package org.javers.spring.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public class JaversAdminAutoConfiguration {


    @Bean
    public StaticController staticController () {
        return new StaticController();
    }

    @Bean
    public MainController mainController() {
        return new MainController();
    }
}
