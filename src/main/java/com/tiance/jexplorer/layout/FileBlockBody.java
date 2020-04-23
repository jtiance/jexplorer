package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.JexplorerApplication;
import com.tiance.jexplorer.UI;
import com.tiance.jexplorer.config.FileDisplaySizeMapper;
import com.tiance.jexplorer.config.PreferenceConfig;
import com.tiance.jexplorer.menu.right.CommonContextMenu;
import com.tiance.jexplorer.menu.right.SpaceContextMenu;
import com.tiance.jexplorer.service.FileService;
import com.tiance.jexplorer.util.FileDisplayUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

@Component
@Scope(value = "prototype")
public class FileBlockBody extends TilePane implements EventHandler<MouseEvent> {

    private NavigationBar navigationBar;

    //当前路径下所有文件
    private ObservableList<AnchorPane> files = FXCollections.observableArrayList();

    //被选择的文件
    private ObservableList<AnchorPane> selectedOnes = FXCollections.observableArrayList();

    @Autowired
    private PreferenceConfig preferenceConfig;

    private FileService fileService;

    @Autowired
    private SpaceContextMenu spaceContextMenu;


    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            selectedOnes.forEach(e -> e.setStyle(""));
//        } else if (event.getButton() == MouseButton.SECONDARY) {
//            SpaceContextMenu scm = new SpaceContextMenu();
//            scm.
        }
    }

    @Autowired
    public FileBlockBody(NavigationBar navigationBar, FileService fileService) {

        this.navigationBar = navigationBar;
        this.fileService = fileService;
        this.setPadding(new Insets(20));
        this.setHgap(40);
        this.setVgap(60);
        this.setStyle("-fx-background-color: white");

        this.setOnMouseClicked(this);
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                spaceContextMenu.show(UI.STAGE, event.getScreenX(), event.getScreenY());
            }
        });

        this.navigationBar.getCurNavigationPathBar().addPathChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue().equals(NavigationPathBar._COMPUTER)) {
                    return;
                }

                files.clear();

                File[] subFiles = new File((String) evt.getNewValue()).listFiles();
                if (subFiles.length == 0) {
                    FileBlockBody.this.getChildren().setAll(FileBlockBody.this.files);
                    return;
                }

                //排序, 首先文件夹, 之后按名称, 以后会自定义排序
                Arrays.sort(subFiles, (o1, o2) -> {
                    if (o1.isDirectory() && o2.isDirectory()) {
                        return o1.getName().compareTo(o2.getName());
                    } else if (o1.isDirectory()) {
                        return -1;
                    } else {
                        return 1;
                    }
                });

                double fileDisplaySize = FileDisplaySizeMapper.getBlockSize(preferenceConfig.getFileDisplaySize());
                double imageDisplaySize = fileDisplaySize * 0.8;

                for (File subFile : subFiles) {
                    if (!preferenceConfig.isShowHidden() && subFile.isHidden()) {
                        continue;
                    }

                    AnchorPane ap = new AnchorPane();
                    ap.setUserData(subFile);
                    ap.setPrefWidth(imageDisplaySize);
                    ap.setPrefHeight(imageDisplaySize);
                    ImageView iv;
                    if (subFile.isDirectory()) {
                        String img = subFile.canRead() ? "imgs/item/folder.png" : "imgs/item/folder_locked.png";
                        InputStream is = this.getClass().getClassLoader().getResourceAsStream(img);
                        iv = new ImageView(new Image(is, imageDisplaySize, imageDisplaySize, true, true));
                    } else {
                        iv = FileDisplayUtil.getImageView(subFile, imageDisplaySize);
                    }

                    ap.getChildren().add(iv);
                    AnchorPane.setTopAnchor(iv, (imageDisplaySize - iv.getImage().getHeight()) / 2);
                    AnchorPane.setLeftAnchor(iv, (fileDisplaySize - iv.getImage().getWidth()) / 2);

                    Text text = new Text(subFile.getName());
                    text.setWrappingWidth(fileDisplaySize);
                    text.setTextAlignment(TextAlignment.CENTER);
                    ap.getChildren().add(text);
                    AnchorPane.setTopAnchor(text, imageDisplaySize + 10);

                    ap.setOnMouseClicked(new MouseClickEventHandler(FileBlockBody.this.navigationBar.getCurNavigationPathBar(), subFile, selectedOnes, FileBlockBody.this.files));

                    FileBlockBody.this.files.add(ap);

                    ap.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                        @Override
                        public void handle(ContextMenuEvent event) {
                            CommonContextMenu c = new CommonContextMenu();
                            c.show(UI.STAGE, event.getScreenX(), event.getScreenY());
                        }
                    });

                }

                FileBlockBody.this.getChildren().setAll(FileBlockBody.this.files);
            }
        });
    }

    private static class MouseClickEventHandler implements EventHandler<MouseEvent> {

        private NavigationPathBar navigationPathBar;

        private File subFile;

        private ObservableList<AnchorPane> selectedOnes;

        private ObservableList<AnchorPane> files;

        public MouseClickEventHandler(NavigationPathBar navigationPathBar, File subFile, ObservableList<AnchorPane> selectedOnes, ObservableList<AnchorPane> files) {
            this.navigationPathBar = navigationPathBar;
            this.subFile = subFile;
            this.selectedOnes = selectedOnes;
            this.files = files;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                AnchorPane ap = (AnchorPane) event.getSource();
                File file = (File) ap.getUserData();
                if (file.isDirectory()) {
                    if (file.canRead()) {
                        navigationPathBar.changePath(subFile.getAbsolutePath(), true, true);
                    }
                } else if (file.isFile()) {
                    FileService fileService = JexplorerApplication.context.getBean(FileService.class);
                    fileService.openFile(file);
                }
            } else if (event.getClickCount() == 1) {
                AnchorPane newSelected = (AnchorPane) event.getSource();
                if (event.isShortcutDown()) {//ctrl的多选
                    newSelected.setStyle("-fx-background-color: aquamarine");
                    selectedOnes.add(newSelected);
                } else if (event.isShiftDown()) { //shift的多选
                    if (selectedOnes.isEmpty()) {
                        newSelected.setStyle("-fx-background-color: aquamarine");
                        selectedOnes.add(newSelected);

                    } else {
                        AnchorPane first = selectedOnes.get(0);

                        if (selectedOnes.size() > 1) {
                            for (int i = 1; i < selectedOnes.size(); i++) {
                                AnchorPane toRemove = selectedOnes.get(i);
                                toRemove.setStyle("");
                            }
                            selectedOnes.clear();
                            selectedOnes.add(first);
                        }

                        boolean begin = false;
                        int count = 0;
                        for (AnchorPane toSelect : files) {
                            if (!begin && (toSelect == first || toSelect == newSelected)) {
                                begin = true;
                            }

                            if (begin) {
                                toSelect.setStyle("-fx-background-color: aquamarine");
                                selectedOnes.add(toSelect);
                                count++;
                            }
                            if (toSelect == first && toSelect == newSelected) {
                                break;
                            }
                            if (count > 1 && (toSelect == first || toSelect == newSelected)) {
                                break;
                            }
                        }
                    }
                } else {//没有按键盘
                    if (!selectedOnes.isEmpty()) {
                        selectedOnes.forEach(e -> e.setStyle(""));
                    }
                    selectedOnes.clear();

                    newSelected.setStyle("-fx-background-color: aquamarine");
                    selectedOnes.add(newSelected);
                }
            }

            event.consume();
        }

    }


}
