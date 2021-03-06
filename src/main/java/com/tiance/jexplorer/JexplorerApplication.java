package com.tiance.jexplorer;

import com.sun.javafx.application.PlatformImpl;
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
        application.setEnvironment(environment);

        PlatformImpl.startup(() -> {
            //在加载spring context前使initialized为true, 防止spring在生成bean的时候出现Toolkit not initialized问题
            System.out.println();
        });
        context = application.run(args);
        Application.launch(UI.class, args);


    }

}
