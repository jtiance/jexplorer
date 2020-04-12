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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
@Scope(value = "prototype")
public class NavigationPathBar extends HBox {

    private static final Logger logger = LoggerFactory.getLogger(NavigationPathBar.class);

    public static final String _COMPUTER = "_COMPUTER";

    private Map<String, Button> btnMap = new HashMap<>();//复用的按钮

    private ObservableList<Button> btnList = FXCollections.observableArrayList();//待显示的按钮

    private SimpleStringProperty path = new SimpleStringProperty(_COMPUTER);//当前路径

    private boolean addToPrev;

    private boolean clearNext;

    private LinkedList<String> prevPaths = new LinkedList<>();
    private LinkedList<String> nextPaths = new LinkedList<>();

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public NavigationPathBar() {

        initDefaultButtons();

        this.getChildren().addAll(btnList.toArray(new Button[btnList.size()]));

        path.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (NavigationPathBar.this.addToPrev) {
                    addToPrev(oldValue);
                }

                if (path.get().equals(_COMPUTER)) {
                    initDefaultButtons();
                } else {
                    listButtonAsPath(newValue);
                }

                for (int i = 0; i < btnList.size(); i++) {
                    if (i == btnList.size() - 1) {
                        btnList.get(i).setStyle("-fx-background-color: #c2ff07");
                    } else {
                        btnList.get(i).setStyle("-fx-background-color: #f2fff3");
                    }
                }

                NavigationPathBar.this.getChildren().setAll(btnList.toArray(new Button[btnList.size()]));

                if (NavigationPathBar.this.clearNext) {
                    NavigationPathBar.this.nextPaths.clear();
                }
            }
        });
    }

    public boolean hasValidPrevs() {
        return prevPaths.size() > 0;
    }

    public boolean hasValidNexts() {
        return nextPaths.size() > 0;
    }

    public String popPrev() {
        return prevPaths.remove(prevPaths.size() - 1);
    }

    public String popNext() {
        return nextPaths.remove(nextPaths.size() - 1);
    }

    private void addToPrev(String oldPath) {
        prevPaths.add(oldPath);
        if (prevPaths.size() > 20) {
            prevPaths.remove(0);
        }
    }

    public void addCurPathToNext() {
        nextPaths.add(this.path.get());
        if (nextPaths.size() > 20) {
            nextPaths.remove(0);
        }
    }

    private void initDefaultButtons() {
        Button button = btnMap.get(_COMPUTER);
        if (button == null) {
            button = new Button("计算机");
            button.setStyle("-fx-background-color: #c2ff07");
            btnMap.put(_COMPUTER, button);
        }

        btnList.clear();
        btnList.add(button);
    }

    private void listButtonAsPath(String path) {
        if (path.equals("/")) {
            btnList.clear();
            Button button = btnMap.get(path);
            if (button == null) {
                button = new Button("根目录");
                button.setOnMouseClicked((event) -> {
                    changePath(path, true, true);
                });
                btnMap.put(path, button);
            }
            btnList.add(button);
        } else {
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
                    button.setOnMouseClicked((event) -> {
                        changePath(folder, true, true);
                    });
                    btnMap.put(folder, button);
                }
                if (folder.equals(System.getenv("HOME"))) {
                    btnList.clear();
                }

                btnList.add(button);
            }
        }
    }

    public void changePath(String path, boolean addToPrev, boolean clearNext) {
        logger.info("change path: {}", path);

        this.addToPrev = addToPrev;
        this.clearNext = clearNext;
        this.path.set(path);

        pcs.firePropertyChange(new PropertyChangeEvent(this, "path", this.path, path));
    }

    public String getPath() {
        return path.get();
    }

    public String getFolderName() {
        if (path.get().equals(_COMPUTER)) {
            return "计算机";
        } else {
            File file = new File(path.get());
            if (file.getName().equals("")) {
                return "根目录";
            } else {
                return file.getName();
            }
        }
    }

    public void addPathChangeListener(PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener("path", propertyChangeListener);
    }

    public void removePathChangeListener(PropertyChangeListener propertyChangeListener) {
        pcs.removePropertyChangeListener("path", propertyChangeListener);
    }


}
