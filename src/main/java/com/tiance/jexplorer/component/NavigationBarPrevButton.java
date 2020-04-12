package com.tiance.jexplorer.component;

import com.tiance.jexplorer.layout.NavigationPathBar;

import java.beans.PropertyChangeListener;

public class NavigationBarPrevButton extends NavigationBarButton {

    private NavigationPathBar existingNavigationPathBar;

    private PropertyChangeListener propertyChangeListener;

    public NavigationBarPrevButton() {
        setText("<");
    }

    public void relistenPathChange(NavigationPathBar curNavigationPathBar) {

        if (existingNavigationPathBar != null) {
            existingNavigationPathBar.removePathChangeListener(propertyChangeListener);
        }
        render(curNavigationPathBar);
        curNavigationPathBar.addPathChangeListener(this.propertyChangeListener = evt -> render(curNavigationPathBar));
        existingNavigationPathBar = curNavigationPathBar;

        this.setOnMouseClicked(e -> {
            if (existingNavigationPathBar.hasValidPrevs()) {
                existingNavigationPathBar.addCurPathToNext();
                String s = existingNavigationPathBar.popPrev();
                existingNavigationPathBar.changePath(s, false, false);
            }
        });
    }

    private void render(NavigationPathBar curNavigationPathBar) {
        boolean valid = curNavigationPathBar == null ? false : curNavigationPathBar.hasValidPrevs();

        if (valid) {
            this.setDisabled(false);
            this.setStyle("-fx-background-color: #c2ff07");
        } else {
            this.setDisabled(true);
            this.setStyle("-fx-background-color: darkgray");
        }

    }

}
