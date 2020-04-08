package com.tiance.jexplorer.component;

import com.tiance.jexplorer.layout.NavigationPathBar;
import org.springframework.stereotype.Component;

@Component
public class NavigationBarUpButton extends NavigationBarButton {

    private NavigationPathBar navigationPathBar;

    public NavigationBarUpButton(NavigationPathBar navigationPathBar) {
        setText("^");

        this.navigationPathBar = navigationPathBar;
    }

}
