package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.config.PreferenceConfig;
import com.tiance.jexplorer.menu.CommonMenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 主界面
 */
@Service
public class MainInterface extends BorderPane {

    private CommonMenuBar commonMenuBar;

    private NavigationBar navigationBar;

    private GeneralFolder generalFolder;

    private FileListBody fileListBody;

    private FileBlockBody fileBlockBody;

    @Autowired
    private PreferenceConfig preferenceConfig;

    @Autowired
    public MainInterface(CommonMenuBar commonMenuBar,
                         NavigationBar navigationBar,
                         GeneralFolder generalFolder,
                         FileListBody fileListBody,
                         FileBlockBody fileBlockBody,
                         PreferenceConfig preferenceConfig) {
        this.commonMenuBar = commonMenuBar;
        this.navigationBar = navigationBar;
        this.generalFolder = generalFolder;
        this.fileListBody = fileListBody;
        this.fileBlockBody = fileBlockBody;
        this.preferenceConfig = preferenceConfig;

        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.commonMenuBar, this.navigationBar);

        this.setTop(vBox);
        this.setLeft(this.generalFolder);

        if (this.preferenceConfig.getFileDisplayStyle() == 1) {

        } else {
            ScrollPane scrollPane = new ScrollPane(this.fileBlockBody);
            scrollPane.prefWidthProperty().bind(this.widthProperty().subtract(this.generalFolder.widthProperty()));
            scrollPane.prefHeightProperty().bind(this.heightProperty().subtract(this.navigationBar.heightProperty()).subtract(commonMenuBar.heightProperty()).subtract(20));

            this.fileBlockBody.prefWidthProperty().bind(this.widthProperty().subtract(this.generalFolder.widthProperty()).subtract(20));
            this.fileBlockBody.prefHeightProperty().bind(this.heightProperty().subtract(this.navigationBar.heightProperty()).subtract(commonMenuBar.heightProperty()).subtract(20));

            this.setCenter(scrollPane);
        }


    }
}
