package com.tiance.jexplorer;

import com.tiance.jexplorer.config.SizeConfig;
import com.tiance.jexplorer.layout.MainInterface;
import com.tiance.jexplorer.util.JsonUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UI extends Application {
    @Override
    public void start(Stage stage) {
        Logger logger = LoggerFactory.getLogger(UI.class);

        MainInterface mainInterface = JexplorerApplication.context.getBean(MainInterface.class);
        Scene scene = new Scene(mainInterface);
        stage.setScene(scene);

        SizeConfig sizeConfig = JexplorerApplication.context.getBean(SizeConfig.class);
        logger.info("sizeConfig: {}", JsonUtil.toJsonString(sizeConfig));

        if (sizeConfig.isStageMaximized()) {
            stage.setMaximized(sizeConfig.isStageMaximized());
        } else {
            stage.setWidth(sizeConfig.getStageWidth());
            stage.setHeight(sizeConfig.getStageHeight());
        }

        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
}
