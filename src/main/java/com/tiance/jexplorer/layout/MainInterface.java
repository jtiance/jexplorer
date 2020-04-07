package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.menu.CommonMenuBar;
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

    @Autowired
    public MainInterface(CommonMenuBar commonMenuBar,
                         NavigationBar navigationBar,
                         GeneralFolder generalFolder) {
        this.commonMenuBar = commonMenuBar;
        this.navigationBar = navigationBar;
        this.generalFolder = generalFolder;

        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.commonMenuBar, this.navigationBar, this.generalFolder);

        this.setTop(vBox);
        this.setLeft(generalFolder);
    }
}
