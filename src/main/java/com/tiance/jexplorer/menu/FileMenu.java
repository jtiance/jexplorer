package com.tiance.jexplorer.menu;

import javafx.scene.control.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileMenu extends Menu {

    private SettingMenuItem settingMenuItem;

    @Autowired
    public FileMenu(SettingMenuItem settingMenuItem) {
        super("文件(_F)");
        setMnemonicParsing(true);

        this.settingMenuItem = settingMenuItem;

        this.getItems().addAll(this.settingMenuItem);

    }
}
