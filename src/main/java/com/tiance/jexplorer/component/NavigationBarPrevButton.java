package com.tiance.jexplorer.component;

import com.tiance.jexplorer.layout.NavigationPathBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Component
public class NavigationBarPrevButton extends NavigationBarButton {

    private NavigationPathBar navigationPathBar;

    private NavigationBarNextButton navigationBarNextButton;

    @Autowired
    public NavigationBarPrevButton(NavigationPathBar navigationPathBar,
                                   NavigationBarNextButton navigationBarNextButton) {
        setText("<");

        this.navigationPathBar = navigationPathBar;
        this.navigationBarNextButton = navigationBarNextButton;

        render();

        navigationPathBar.addPathChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                render();
            }
        });

        this.setOnMouseClicked(e -> {
            this.navigationPathBar.addCurPathToNext();
            this.navigationPathBar.changePath(this.navigationPathBar.popPrev(), false);
        });
    }

    private void render() {
        boolean valid = this.navigationPathBar.hasValidPrevs();

        if (valid) {
            this.setDisabled(false);
            this.setStyle("-fx-background-color: #c2ff07");
        } else {
            this.setDisabled(true);
            this.setStyle("-fx-background-color: darkgray");
        }

    }

}
