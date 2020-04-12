package com.tiance.jexplorer.menu;

import com.tiance.jexplorer.layout.MainInterface;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AddTabMenuItem extends MenuItem implements EventHandler {

    private MainInterface mainInterface;

    @Autowired
    @Lazy
    public AddTabMenuItem(MainInterface mainInterface) {
        setText("打开新窗口(_N)");
        setMnemonicParsing(true);

        this.mainInterface = mainInterface;

        this.setOnAction(this);
    }

    @Override
    public void handle(Event event) {
        mainInterface.addNewTab();
    }
}
