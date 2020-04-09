package com.tiance.jexplorer.layout;

import com.sun.javafx.scene.control.skin.TitledPaneSkin;
import com.tiance.jexplorer.config.ItemStyle;
import com.tiance.jexplorer.config.PopularSiderFolder;
import com.tiance.jexplorer.config.PreferenceConfig;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

@Component
public class ChiefBody extends VBox {

    private FlowPane foldersInHome = new FlowPane();//主目录的文件夹面板
    private FlowPane disks = new FlowPane(); //磁盘面板

    private PreferenceConfig preferenceConfig;

    private NavigationPathBar navigationPathBar;

    @Autowired
    public ChiefBody(NavigationPathBar navigationPathBar,
                     PreferenceConfig preferenceConfig) {
        this.navigationPathBar = navigationPathBar;
        this.preferenceConfig = preferenceConfig;

        loadPopularFolders();
        TitledPane myFolder = new TitledPane();
        myFolder.setContent(foldersInHome);
        myFolder.setText("我的目录");
        TitledPaneSkin skin = new TitledPaneSkin(myFolder);//todo

        loadDisks();
        TitledPane diskFolder = new TitledPane();
        diskFolder.setContent(disks);
        diskFolder.setText("我的磁盘");

        this.getChildren().addAll(myFolder, diskFolder);

    }

    private void loadDisks() {
        File root = new File("/");
        AnchorPane rootAnchorPane = initFolder(root, "根目录", ItemStyle.getImagePath("/"), 140d);
        disks.getChildren().add(rootAnchorPane);

        String home = System.getenv("HOME");
        String media = home.replace("home", "media");
        File mediaFolder = new File(media);
        File[] files = mediaFolder.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().startsWith("_") ? -1 : 1;
            }
        });
        for (File file : files) {
            String imgKey = file.getName().startsWith("_") ? "internalDisk" : "externalDisk";
            AnchorPane anchorPane = initFolder(file, file.getName(), ItemStyle.getImagePath(imgKey), 140d);
            disks.getChildren().add(anchorPane);
        }

        disks.setHgap(30);
        disks.setVgap(30);
        disks.setPadding(new Insets(30));
        disks.setStyle("-fx-background-color: white");
    }

    private void loadPopularFolders() {
        String home = System.getenv("HOME");
        File homeFolder = new File(home);
        File[] files = homeFolder.listFiles();

        for (File file : files) {
            if (PopularSiderFolder.contains(file.getName())) {

                AnchorPane anchorPane = initFolder(file, file.getName(), ItemStyle.getImagePath(file.getName()), 140d);
                foldersInHome.getChildren().add(anchorPane);
            }
        }

        foldersInHome.setHgap(30);
        foldersInHome.setVgap(30);
        foldersInHome.setPadding(new Insets(30));
        foldersInHome.setStyle("-fx-background-color: white");
    }

    private AnchorPane initFolder(File file, String alias, String imgPath, double fileDisplaySize) {
        double imageDisplaySize = fileDisplaySize * 0.8;
        double imageCorner = fileDisplaySize * 0.1;

        String folderName = StringUtils.isEmpty(PopularSiderFolder.translate(file.getName())) ? alias : file.getName();
        AnchorPane ap = new AnchorPane();
        ap.setUserData(file);
        ap.setPrefWidth(imageDisplaySize);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(imgPath);
        ImageView iv = new ImageView(new Image(is, imageDisplaySize, imageDisplaySize, true, true));

        ap.getChildren().add(iv);
        AnchorPane.setTopAnchor(iv, imageCorner);
        AnchorPane.setLeftAnchor(iv, imageCorner);

        Text text = new Text(folderName);
        text.setWrappingWidth(fileDisplaySize);
        text.setTextAlignment(TextAlignment.CENTER);
        ap.getChildren().add(text);
        AnchorPane.setTopAnchor(text, imageDisplaySize + 20);

        ap.setOnMouseClicked(new MouseClickEventHandler(this, navigationPathBar, file));
        return ap;
    }

    private static class MouseClickEventHandler implements EventHandler<MouseEvent> {

        private ChiefBody chiefBody;

        private NavigationPathBar navigationPathBar;

        private File subFile;

        public MouseClickEventHandler(ChiefBody chiefBody, NavigationPathBar navigationPathBar, File subFile) {
            this.chiefBody = chiefBody;
            this.navigationPathBar = navigationPathBar;
            this.subFile = subFile;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                navigationPathBar.changePath(subFile.getAbsolutePath(), true, true);
                AnchorPane newSelected = (AnchorPane) event.getSource();
                newSelected.setStyle("");

            } else if (event.getClickCount() == 1) {
                ObservableList<Node> folders = chiefBody.foldersInHome.getChildren();
                ObservableList<Node> disks = chiefBody.disks.getChildren();

                for (Node node : folders) {
                    node.setStyle("");
                }
                for (Node node : disks) {
                    node.setStyle("");
                }

                AnchorPane newSelected = (AnchorPane) event.getSource();
                newSelected.setStyle("-fx-background-color: aquamarine");
            }

            event.consume();
        }

    }
}
