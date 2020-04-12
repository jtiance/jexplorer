package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.JexplorerApplication;
import com.tiance.jexplorer.component.NavigationBarNextButton;
import com.tiance.jexplorer.component.NavigationBarPrevButton;
import com.tiance.jexplorer.component.NavigationBarUpButton;
import com.tiance.jexplorer.config.PreferenceConfig;
import com.tiance.jexplorer.menu.CommonMenuBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 主界面
 */
@Service
@Lazy
public class MainInterface extends BorderPane {

    private CommonMenuBar commonMenuBar;

    private NavigationBar navigationBar;

    private GeneralFolder generalFolder;

    private PreferenceConfig preferenceConfig;

    public TabPane tabPane = new TabPane();

    @Autowired
    public MainInterface(CommonMenuBar commonMenuBar,
                         GeneralFolder generalFolder,
                         NavigationBar navigationBar,
                         PreferenceConfig preferenceConfig) {
        this.commonMenuBar = commonMenuBar;
        this.generalFolder = generalFolder;
        this.preferenceConfig = preferenceConfig;
        this.navigationBar = navigationBar;

        NavigationBarPrevButton navigationBarPrevButton = new NavigationBarPrevButton();
        NavigationBarUpButton navigationBarUpButton = new NavigationBarUpButton();
        NavigationBarNextButton navigationBarNextButton = new NavigationBarNextButton();

        navigationBar.addChildren(navigationBarPrevButton, navigationBarUpButton, navigationBarNextButton);

        this.setCenter(tabPane);
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MainInterface.this.navigationBar.selectNavigationPathBar((int) newValue);
            }
        });
        addNewTab();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.commonMenuBar, this.navigationBar);

        this.setTop(vBox);
        this.setLeft(this.generalFolder);


    }

//    public ChiefBody getCurChiefBody() {
//        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
//        ScrollPane scrollPane = (ScrollPane) selectedItem.getContent();
//
//        Node content = scrollPane.getContent();
//        if (content instanceof ChiefBody) {
//            return (ChiefBody) content;
//        } else {
//            return (ChiefBody) scrollPane.getUserData();
//        }
//    }
//
//    public FileBlockBody getCurFileBlockBody() {
//        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
//        ScrollPane scrollPane = (ScrollPane) selectedItem.getContent();
//
//        Node content = scrollPane.getContent();
//        if (content instanceof ChiefBody) {
//            return (FileBlockBody) content;
//        } else {
//            return (FileBlockBody) scrollPane.getUserData();
//        }
//    }


    public void addNewTab() {
        NavigationPathBar navigationPathBar = this.navigationBar.addNewNavigationPathBar();
        navigationPathBar.addPathChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String path = (String) evt.getNewValue();

                Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
                ScrollPane scrollPane = (ScrollPane) selectedItem.getContent();
                Node content = scrollPane.getContent();

                if (path.equals(NavigationPathBar._COMPUTER)) {
                    if (content instanceof ChiefBody) {
                        return;
                    } else {
                        ChiefBody chiefBody = (ChiefBody) scrollPane.getUserData();
                        scrollPane.setUserData(content);
                        scrollPane.setContent(chiefBody);
                    }
                } else {
                    if (MainInterface.this.preferenceConfig.getFileDisplayStyle() == 1) {
                        //todo 列表形态
                    } else {
                        if (content instanceof FileBlockBody) {
                            return;
                        } else {
                            FileBlockBody fileBlockBody = (FileBlockBody) scrollPane.getUserData();
                            scrollPane.setContent(fileBlockBody);
                            scrollPane.setUserData(content);
                        }
                    }
                }
            }
        });

        Tab tab = new Tab("计算机");
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        tab.setUserData(navigationPathBar);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(300d);
        scrollPane.setMinWidth(300d);

        tab.setContent(scrollPane);
        int size = tabPane.getTabs().size();
        if (size == 1) {
            tab.setClosable(false);
        } else {
            tabPane.getTabs().forEach(e -> e.setClosable(true));
        }
        tab.setOnClosed(e -> {
            e.getSource();
            if (tabPane.getTabs().size() == 1) {
                tabPane.getTabs().get(0).setClosable(false);
            }
            MainInterface.this.navigationBar.removeNavigationPathBar(navigationPathBar);
        });
        navigationPathBar.addPathChangeListener(evt -> {
            String folderName = navigationPathBar.getFolderName();
            tab.setText(folderName);
        });


        ChiefBody chiefBody = JexplorerApplication.context.getBean(ChiefBody.class);
        chiefBody.prefWidthProperty().bind(MainInterface.this.widthProperty().subtract(MainInterface.this.generalFolder.widthProperty()).subtract(2));
        chiefBody.prefHeightProperty().bind(MainInterface.this.heightProperty().subtract(commonMenuBar.heightProperty()).subtract(100));
        scrollPane.setContent(chiefBody);

        FileBlockBody fileBlockBody = JexplorerApplication.context.getBean(FileBlockBody.class);
        fileBlockBody.prefWidthProperty().bind(MainInterface.this.widthProperty().subtract(MainInterface.this.generalFolder.widthProperty()).subtract(2));
        fileBlockBody.prefHeightProperty().bind(MainInterface.this.heightProperty().subtract(commonMenuBar.heightProperty()).subtract(100));
        scrollPane.setUserData(fileBlockBody);


    }
}
