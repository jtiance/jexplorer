package com.tiance.jexplorer;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class JexplorerApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(JexplorerApplication.class);
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("default");
        application.setEnvironment(environment);
        context = application.run(args);

        Application.launch(UI.class);

    }

}
