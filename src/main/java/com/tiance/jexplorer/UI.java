package com.tiance.jexplorer;

import com.tiance.jexplorer.layout.MainInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UI extends Application {
    @Override
    public void start(Stage stage) {

        MainInterface mainInterface = JexplorerApplication.context.getBean(MainInterface.class);

        Scene scene = new Scene(mainInterface);
        stage.setScene(scene);

        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
}
