package com.tiance.jexplorer.menu.right;

import com.tiance.jexplorer.service.TerminalService;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpaceContextMenu extends ContextMenu {

    @Autowired
    private TerminalService terminalService;

    public SpaceContextMenu() {
        MenuItem menuItem = new MenuItem("打开终端");
        menuItem.setOnAction((e) -> {
            terminalService.openTerminal();
        });
        this.getItems().add(menuItem);
    }
}
