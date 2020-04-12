package com.tiance.jexplorer.menu;

import javafx.scene.control.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileMenu extends Menu {

    private SettingMenuItem settingMenuItem;
    private AddTabMenuItem addTabMenuItem;

    @Autowired
    public FileMenu(SettingMenuItem settingMenuItem,
                    AddTabMenuItem addTabMenuItem) {
        super("文件(_F)");
        setMnemonicParsing(true);

        this.settingMenuItem = settingMenuItem;
        this.addTabMenuItem = addTabMenuItem;

        this.getItems().addAll(this.settingMenuItem, this.addTabMenuItem);

    }
}
