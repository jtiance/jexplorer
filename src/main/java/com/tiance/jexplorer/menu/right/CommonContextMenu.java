package com.tiance.jexplorer.menu.right;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class CommonContextMenu extends ContextMenu {

    public CommonContextMenu() {

        MenuItem menuItem = new MenuItem("复制");
        this.getItems().add(menuItem);
    }
}
