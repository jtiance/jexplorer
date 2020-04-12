package com.tiance.jexplorer.layout;

import com.tiance.jexplorer.JexplorerApplication;
import com.tiance.jexplorer.component.NavigationBarNextButton;
import com.tiance.jexplorer.component.NavigationBarPrevButton;
import com.tiance.jexplorer.component.NavigationBarUpButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component
public class NavigationBar extends HBox {

    private NavigationBarPrevButton navigationBarPrevButton;

    private NavigationBarNextButton navigationBarNextButton;

    private NavigationBarUpButton navigationBarUpButton;

    private List<NavigationPathBar> navigationPathBars = new LinkedList<>();

    private NavigationPathBar curNavigationPathBar;

    private Logger logger = LoggerFactory.getLogger(NavigationBar.class);

    @Autowired
    public NavigationBar(NavigationBarPrevButton navigationBarPrevButton,
                         NavigationBarUpButton navigationBarUpButton,
                         NavigationBarNextButton navigationBarNextButton ) {
        this.navigationBarPrevButton = navigationBarPrevButton;
        this.navigationBarNextButton = navigationBarNextButton;
        this.navigationBarUpButton = navigationBarUpButton;

        this.addChildren();

        setStyle("-fx-background-color: #cfced1");
        setPrefHeight(40d);

        setAlignment(Pos.CENTER_LEFT);
    }

    public void addChildren() {
        ImageView icon = getIcon();
        this.getChildren().addAll(icon, navigationBarPrevButton, navigationBarUpButton, navigationBarNextButton);

        setMargin(icon, new Insets(0, 20, 0, 15));

        Label placeHolder = new Label();
        this.getChildren().add(placeHolder);
    }

    private ImageView getIcon() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("imgs/jexplorer.png");
        Image image = new Image(is, 24, 24, true, true);
        ImageView iv = new ImageView(image);
        return iv;
    }

    public NavigationPathBar getCurNavigationPathBar() {
        return this.curNavigationPathBar;
    }

    public void selectNavigationPathBar(int index) {
        curNavigationPathBar = navigationPathBars.get(index);
        this.getChildren().set(4, curNavigationPathBar);

        this.navigationBarPrevButton.relistenPathChange(curNavigationPathBar);
        this.navigationBarUpButton.relistenPathChange(curNavigationPathBar);
        this.navigationBarNextButton.relistenPathChange(curNavigationPathBar);

        logger.info("change to path bar, index {}, path:{}", index, curNavigationPathBar.getPath());
    }

    public NavigationPathBar addNewNavigationPathBar() {
        curNavigationPathBar = JexplorerApplication.context.getBean(NavigationPathBar.class);
        navigationPathBars.add(curNavigationPathBar);
        this.getChildren().set(4, curNavigationPathBar); //第4个位置为导航栏
        setMargin(curNavigationPathBar, new Insets(5, 20, 0, 15));

        this.navigationBarPrevButton.relistenPathChange(curNavigationPathBar);
        this.navigationBarUpButton.relistenPathChange(curNavigationPathBar);
        this.navigationBarNextButton.relistenPathChange(curNavigationPathBar);

        return this.curNavigationPathBar;
    }

    public void removeNavigationPathBar(NavigationPathBar navigationPathBar) {
        int index = -1;
        for (Iterator<NavigationPathBar> iter = navigationPathBars.iterator(); iter.hasNext(); ) {
            index++;
            NavigationPathBar next = iter.next();
            if (next == navigationPathBar) {
                iter.remove();
                break;
            }
        }
        if (index > 0) {
            index--;
        }
        selectNavigationPathBar(index);
    }


}
