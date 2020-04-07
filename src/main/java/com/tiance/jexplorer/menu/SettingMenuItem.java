package com.tiance.jexplorer.menu;

import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class SettingMenuItem extends MenuItem {

    public SettingMenuItem() {
        setText("设置(_S)");
        setMnemonicParsing(true);
    }
}
