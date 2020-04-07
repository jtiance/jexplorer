package com.tiance.jexplorer.menu;

import javafx.scene.control.MenuBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonMenuBar extends MenuBar {

    private FileMenu fileMenu;

    @Autowired
    public CommonMenuBar(FileMenu fileMenu) {
        this.fileMenu = fileMenu;
        this.getMenus().addAll(fileMenu);
    }
}
