package com.tiance.jexplorer.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Scope(value = "singleton")
public class TitleBar extends HBox {

    public TitleBar() {
        setStyle("-fx-background-color: #cfced1");
        setPrefHeight(40d);

        addChildren();
        setAlignment(Pos.CENTER_LEFT);

    }

    private void addChildren() {
        ImageView icon = getIcon();
        this.getChildren().add(icon);
        setMargin(icon, new Insets(0, 20, 0, 15));

    }

    private ImageView getIcon() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("jexplorer.png");
        Image image = new Image(is, 24, 24, true, true);
        ImageView iv = new ImageView(image);
        return iv;
    }


}
