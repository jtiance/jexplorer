package com.tiance.jexplorer;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JexplorerApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(JexplorerApplication.class);

        Application.launch(UI.class);

    }

}
