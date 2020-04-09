package com.tiance.jexplorer.component;

import com.tiance.jexplorer.layout.NavigationPathBar;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Component
public class NavigationBarNextButton extends NavigationBarButton {

    private NavigationPathBar navigationPathBar;

    public NavigationBarNextButton(NavigationPathBar navigationPathBar) {
        setText(">");
        this.navigationPathBar = navigationPathBar;

        render();

        navigationPathBar.addPathChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                render();
            }
        });

        this.setOnMouseClicked(e -> {
            if (this.navigationPathBar.hasValidNexts()) {
                String s = this.navigationPathBar.popNext();
                this.navigationPathBar.changePath(s, true, false);
            }
        });
    }

    private void render() {
        boolean valid = this.navigationPathBar.hasValidNexts();

        if (valid) {
            this.setDisabled(false);
            this.setStyle("-fx-background-color: #c2ff07");
        } else {
            this.setDisabled(true);
            this.setStyle("-fx-background-color: darkgray");
        }

    }
}
