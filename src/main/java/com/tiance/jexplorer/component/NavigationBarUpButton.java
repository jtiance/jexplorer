package com.tiance.jexplorer.component;

import com.tiance.jexplorer.layout.NavigationPathBar;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

@Component
public class NavigationBarUpButton extends NavigationBarButton {

    private NavigationPathBar navigationPathBar;

    private File curPath;

    public NavigationBarUpButton(NavigationPathBar navigationPathBar) {
        setText("^");

        this.navigationPathBar = navigationPathBar;

        render("/");

        this.navigationPathBar.addPathChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                String newValue = (String) evt.getNewValue();
                if (newValue.equals(NavigationPathBar._COMPUTER)) {
                    curPath = null;
                } else {
                    curPath = new File(newValue);
                }
                render(newValue);
            }
        });

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (curPath != null && !curPath.getName().equals("")) {
                    NavigationBarUpButton.this.navigationPathBar.changePath(curPath.getParent(), true, true);

                }
            }
        });
    }

    private void render(String path) {

        if (path.equals("/") || path.equals(NavigationPathBar._COMPUTER)) {
            this.setDisabled(true);
            this.setStyle("-fx-background-color: darkgray");
        } else {
            this.setDisabled(false);
            this.setStyle("-fx-background-color: #c2ff07");
        }

    }

}
