package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.component.NavigationBarNextButton;
import com.tiance.jexplorer.component.NavigationBarPrevButton;
import com.tiance.jexplorer.component.NavigationBarUpButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class NavigationBar extends HBox {

    private NavigationBarPrevButton navigationBarPrevButton;

    private NavigationBarNextButton navigationBarNextButton;

    private NavigationBarUpButton navigationBarUpButton;

    private NavigationPathBar navigationPathBar;

    @Autowired
    public NavigationBar(NavigationBarPrevButton navigationBarPrevButton,
                         NavigationBarNextButton navigationBarNextButton,
                         NavigationBarUpButton navigationBarUpButton,
                         NavigationPathBar navigationPathBar) {
        this.navigationBarPrevButton = navigationBarPrevButton;
        this.navigationBarNextButton = navigationBarNextButton;
        this.navigationBarUpButton = navigationBarUpButton;
        this.navigationPathBar = navigationPathBar;

        setStyle("-fx-background-color: #cfced1");
        setPrefHeight(40d);

        addChildren();
        setAlignment(Pos.CENTER_LEFT);
    }

    private void addChildren() {
        ImageView icon = getIcon();
        this.getChildren().addAll(icon, navigationBarPrevButton, navigationBarUpButton, navigationBarNextButton, navigationPathBar);
        setMargin(icon, new Insets(0, 20, 0, 15));
        setMargin(navigationPathBar, new Insets(5, 20, 0, 15));

    }

    private ImageView getIcon() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("imgs/jexplorer.png");
        Image image = new Image(is, 24, 24, true, true);
        ImageView iv = new ImageView(image);
        return iv;
    }


}
