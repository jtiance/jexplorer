package com.tiance.jexplorer.layout;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

@Component
public class NavigationPathBar extends HBox {

    private static final Logger logger = LoggerFactory.getLogger(NavigationPathBar.class);

    public static final String _COMPUTER = "_COMPUTER";

    private Map<String, Button> btnMap = new HashMap<>();//复用的按钮

    private ObservableList<Object> btnList = FXCollections.observableArrayList();//待显示的按钮

    private SimpleStringProperty path = new SimpleStringProperty();//当前路径

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public NavigationPathBar() {

        initDefaultButtons();

        this.getChildren().addAll(btnList.toArray(new Button[btnList.size()]));

        path.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (path.get().equals(_COMPUTER)) {
                    initDefaultButtons();
                } else {
                    listButtonAsPath(newValue);
                }

                for (int i = 0; i < btnList.size(); i++) {
                    if (i == btnList.size() - 1) {
                        ((Button) btnList.get(i)).setStyle("-fx-background-color: aqua");
                    } else {
                        ((Button) btnList.get(i)).setStyle("-fx-background-color: #e0ffd8");
                    }
                }

                NavigationPathBar.this.getChildren().setAll(btnList.toArray(new Button[btnList.size()]));
            }
        });
    }

    private void initDefaultButtons() {
        Button button = btnMap.get(_COMPUTER);
        if (button == null) {
            button = new Button("计算机");
            button.setStyle("-fx-background-color: aqua");
            btnMap.put(_COMPUTER, button);
        }

        btnList.clear();
        btnList.add(button);
    }

    private void listButtonAsPath(String path) {
        String[] split = path.split("/");
        StringBuilder sb = new StringBuilder();
        btnList.clear();

        for (String s : split) {
            if (sb.toString().equals("/")) {
                sb.append(s);
            } else {
                sb.append("/").append(s);
            }

            String folder = sb.toString();
            Button button = btnMap.get(sb.toString());
            if (button == null) {
                if (folder.equals("/")) {
                    button = new Button("根目录");
                } else if (folder.equals(System.getenv("HOME"))) {
                    button = new Button("主目录");
                } else {
                    button = new Button(s);
                }
            }

            if (folder.equals(System.getenv("HOME"))) {
                btnList.clear();
            }
            btnMap.put(folder, button);
            btnList.add(button);
        }
    }

    public void changePath(String path) {
        logger.info("change path: {}", path);
        this.path.set(path);


        pcs.firePropertyChange(new PropertyChangeEvent(this, "path", this.path, path));
    }

    public void listenPath(PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener("path", propertyChangeListener);
    }


}
