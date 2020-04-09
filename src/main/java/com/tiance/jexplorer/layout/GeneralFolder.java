package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.config.PopularFolder;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeneralFolder extends VBox {

    private NavigationPathBar navigationPathBar;

    private SimpleBooleanProperty toComputer = new SimpleBooleanProperty(false);

    @Autowired
    public GeneralFolder(NavigationPathBar navigationPathBar) {
        this.navigationPathBar = navigationPathBar;

        this.setStyle("-fx-background-color: rgba(209,255,248,0.07)");
        this.setPrefWidth(250d);
        this.setMinWidth(100d);
        this.setMaxWidth(300d);

        this.setAlignment(Pos.TOP_LEFT);
        this.setPadding(new Insets(20, 10, 30, 10));

        //计算机, 特殊的全展现界面
        Label label1 = initFolder("计算机", NavigationPathBar._COMPUTER, "imgs/folder/computer.png");
        this.getChildren().add(label1);

        //主目录/home/alex
        Label label2 = initFolder("主目录", System.getenv("HOME"), "imgs/folder/main.png");
        this.getChildren().add(label2);

        listFoldersInChiefDirectory();
    }

    /**
     * 显示主目录下的文件夹, 主目录为/home/alex
     */
    private void listFoldersInChiefDirectory() {
        String home = System.getenv("HOME");
        File homeFolder = new File(home);
        File[] files = homeFolder.listFiles();

        List<File> deferredFolders = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                continue;
            }
            if (file.isHidden()) {
                continue;
            }

            if (PopularFolder.contains(file.getName())) {
                Label label = initFolder(file.getName() + " [" + PopularFolder.translate(file.getName()) + "]", file.getAbsolutePath(), PopularFolder.getImagePath(file.getName()));
                this.getChildren().add(label);
            } else {
                deferredFolders.add(file);
            }
        }
        for (File deferred : deferredFolders) {
            Label label = initFolder(deferred.getName(), deferred.getAbsolutePath(), "imgs/folder/folder.png");
            this.getChildren().add(label);
        }
    }

    private Label initFolder(String folderName, String folderPath, String imgPath) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(imgPath);
        ImageView iv = new ImageView(new Image(is, 16, 16, true, false));
        Label label = new Label(folderName, iv);
        label.setFont(new Font(14));
        label.setPrefHeight(30d);

        label.setStyle("-fx-background-color: rgba(209,255,248,0.07)");
        label.prefWidthProperty().bind(this.widthProperty());
        if (folderPath.equals(NavigationPathBar._COMPUTER)) {
            label.setOnMouseClicked(e -> {
                toComputer.setValue(true);
            });
        } else {
            label.setOnMouseClicked(event -> navigationPathBar.changePath(folderPath, true, true));
            toComputer.setValue(false);
        }

        label.setOnMouseEntered((event) -> {
            label.setStyle("-fx-background-color: #c2ff07; -fx-text-fill: whitesmoke;");
        });
        label.setOnMouseExited((event) -> {
            label.setStyle("-fx-background-color: rgba(209,255,248,0.07)");
        });
        return label;
    }

    public SimpleBooleanProperty toComputerProperty() {
        return toComputer;
    }


}
