package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.config.FileDisplaySizeMapper;
import com.tiance.jexplorer.config.PopularFolder;
import com.tiance.jexplorer.config.PreferenceConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class ChiefBody extends VBox {

    private HBox foldersInHome = new HBox();

    private PreferenceConfig preferenceConfig;

    private NavigationPathBar navigationPathBar;

    @Autowired
    public ChiefBody(NavigationPathBar navigationPathBar,
                     PreferenceConfig preferenceConfig) {
        this.navigationPathBar = navigationPathBar;
        this.preferenceConfig = preferenceConfig;

        loadPopularFolders();

        this.getChildren().add(foldersInHome);

    }

    private void loadPopularFolders() {
        String home = System.getenv("HOME");
        File homeFolder = new File(home);
        File[] files = homeFolder.listFiles();

        double fileDisplaySize = FileDisplaySizeMapper.getBlockSize(preferenceConfig.getFileDisplaySize());

        for (File file : files) {
            if (PopularFolder.contains(file.getName())) {
                AnchorPane anchorPane = initFolder(file, file.getAbsolutePath(), PopularFolder.getImagePath(file.getName()), fileDisplaySize);
                foldersInHome.getChildren().add(anchorPane);
            }
        }
    }

    private AnchorPane initFolder(File file, String folderPath, String imgPath, double fileDisplaySize) {
        double imageDisplaySize = fileDisplaySize * 0.8;
        double imageCorner = fileDisplaySize * 0.1;

        String folderName = file.getName() + " [" + PopularFolder.translate(file.getName()) + "]";
        AnchorPane ap = new AnchorPane();
        ap.setUserData(file);
        ap.setPrefWidth(imageDisplaySize);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("imgs/item/folder.png");
        ImageView iv = new ImageView(new Image(is, imageDisplaySize, imageDisplaySize, true, false));

        ap.getChildren().add(iv);
        AnchorPane.setTopAnchor(iv, imageCorner);
        AnchorPane.setLeftAnchor(iv, imageCorner);

        Text text = new Text(folderName);
        text.setWrappingWidth(fileDisplaySize);
        text.setTextAlignment(TextAlignment.CENTER);
        ap.getChildren().add(text);
        AnchorPane.setTopAnchor(text, imageDisplaySize + 10);
        return ap;
    }
}
