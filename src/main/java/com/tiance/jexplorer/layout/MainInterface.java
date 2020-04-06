package com.tiance.jexplorer.layout;

import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 主界面
 */
@Service
public class MainInterface extends BorderPane {

    @Autowired
    private TitleBar titleBar;

    public MainInterface() {
        titleBar = new TitleBar();
        this.setTop(titleBar);
    }
}
