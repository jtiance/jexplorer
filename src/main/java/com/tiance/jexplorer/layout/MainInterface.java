package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.config.PreferenceConfig;
import com.tiance.jexplorer.menu.CommonMenuBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 主界面
 */
@Service
public class MainInterface extends BorderPane {

    private CommonMenuBar commonMenuBar;

    private NavigationBar navigationBar;

    private GeneralFolder generalFolder;

    private ChiefBody chiefBody;

    private FileListBody fileListBody;

    private FileBlockBody fileBlockBody;

    private NavigationPathBar navigationPathBar;

    private PreferenceConfig preferenceConfig;

    ScrollPane scrollPane = new ScrollPane();

    @Autowired
    public MainInterface(CommonMenuBar commonMenuBar,
                         NavigationBar navigationBar,
                         GeneralFolder generalFolder,
                         ChiefBody chiefBody,
                         FileListBody fileListBody,
                         FileBlockBody fileBlockBody,
                         NavigationPathBar navigationPathBar,
                         PreferenceConfig preferenceConfig) {
        this.commonMenuBar = commonMenuBar;
        this.navigationBar = navigationBar;
        this.generalFolder = generalFolder;
        this.chiefBody = chiefBody;
        this.fileListBody = fileListBody;
        this.fileBlockBody = fileBlockBody;
        this.navigationPathBar = navigationPathBar;
        this.preferenceConfig = preferenceConfig;

        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.commonMenuBar, this.navigationBar);

        this.setTop(vBox);
        this.setLeft(this.generalFolder);

        this.setCenter(scrollPane);
        scrollPane.setMinHeight(300d);
        scrollPane.setMinWidth(300d);
        scrollPane.setContent(MainInterface.this.chiefBody);

        scrollPane.prefWidthProperty().bind(MainInterface.this.widthProperty().subtract(MainInterface.this.generalFolder.widthProperty()));
        scrollPane.prefHeightProperty().bind(MainInterface.this.heightProperty().subtract(MainInterface.this.navigationBar.heightProperty()).subtract(commonMenuBar.heightProperty()).subtract(20));

        MainInterface.this.chiefBody.prefWidthProperty().bind(MainInterface.this.widthProperty().subtract(MainInterface.this.generalFolder.widthProperty()).subtract(20));
        MainInterface.this.chiefBody.prefHeightProperty().bind(MainInterface.this.heightProperty().subtract(MainInterface.this.navigationBar.heightProperty()).subtract(commonMenuBar.heightProperty()).subtract(20));

        MainInterface.this.fileBlockBody.prefWidthProperty().bind(MainInterface.this.widthProperty().subtract(MainInterface.this.generalFolder.widthProperty()).subtract(20));
        MainInterface.this.fileBlockBody.prefHeightProperty().bind(MainInterface.this.heightProperty().subtract(MainInterface.this.navigationBar.heightProperty()).subtract(commonMenuBar.heightProperty()).subtract(20));

        this.navigationPathBar.addPathChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String path = (String)evt.getNewValue();
                if(path.equals(NavigationPathBar._COMPUTER)) {
                    scrollPane.setContent(MainInterface.this.chiefBody);
                }else {
                    if (MainInterface.this.preferenceConfig.getFileDisplayStyle() == 1) {

                    } else {
                        scrollPane.setContent(MainInterface.this.fileBlockBody);
                    }
                }

            }
        });

//        this.generalFolder.toChiefBodyProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean toChiefBody) {
//                if (toChiefBody) {
//                    scrollPane.setContent(MainInterface.this.chiefBody);
//                }
//            }
//        });

    }
}
