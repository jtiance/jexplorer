package com.tiance.jexplorer.component;

import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class NavigationBarPrevButton extends NavigationBarButton {
    public NavigationBarPrevButton() {
        setText("<");
    }

}
