package com.tiance.jexplorer;

import com.tiance.jexplorer.layout.MainInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UI extends Application {
    @Override
    public void start(Stage stage) {

        MainInterface mainInterface = JexplorerApplication.context.getBean(MainInterface.class);
        initDefaultStage(stage, mainInterface);
    }

    private void initDefaultStage(Stage stage, Pane pane) {
        Scene scene = new Scene(pane);

        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.show();
    }
}
