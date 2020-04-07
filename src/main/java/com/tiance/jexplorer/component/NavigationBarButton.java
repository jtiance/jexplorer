package com.tiance.jexplorer.component;

import javafx.scene.control.Button;

public class NavigationBarButton extends Button {
    public NavigationBarButton() {
        setFocused(false);
        setStyle("-fx-background-color: #f0f0f0");
    }

    public void disable() {
        this.setDisabled(true);
    }

}
