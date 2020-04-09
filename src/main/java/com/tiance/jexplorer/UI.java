package com.tiance.jexplorer;

import com.tiance.jexplorer.config.SizeConfig;
import com.tiance.jexplorer.layout.MainInterface;
import com.tiance.jexplorer.util.JsonUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UI extends Application {

    private Logger logger = LoggerFactory.getLogger(UI.class);

    public static Stage STAGE;

    private MainInterface mainInterface;

    @Override
    public void start(Stage stage) {
        this.mainInterface = JexplorerApplication.context.getBean(MainInterface.class);
        Scene scene = new Scene(this.mainInterface);
        stage.setScene(scene);

        stage.initStyle(StageStyle.DECORATED);
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("imgs/jexplorer1.png");
        stage.getIcons().add(new Image(is));

        resize(stage);

        stage.show();

        UI.STAGE = stage;
    }

    private void resize(Stage stage) {
        SizeConfig sizeConfig = JexplorerApplication.context.getBean(SizeConfig.class);
        logger.info("sizeConfig: {}", JsonUtil.toJsonString(sizeConfig)); //todo

        if (sizeConfig.isStageMaximized()) {
            stage.setMaximized(sizeConfig.isStageMaximized());
        } else {
            stage.setWidth(sizeConfig.getStageWidth());
            stage.setHeight(sizeConfig.getStageHeight());
        }
    }
}
