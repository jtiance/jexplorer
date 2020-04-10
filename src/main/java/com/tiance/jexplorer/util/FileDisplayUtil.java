package com.tiance.jexplorer.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileDisplayUtil {

    public static ImageView getImageView(File file, double imageDisplaySize) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        String suffix = "";
        if (index >= 0) {
            suffix = name.substring(index).toLowerCase();
        }

        ImageView iv = null;
        switch (suffix) {
            case ".jpg":
            case ".png": {
                try {
                    InputStream is = new FileInputStream(file);
                    iv = new ImageView(new Image(is, imageDisplaySize, imageDisplaySize, true, true));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
            default: {
                InputStream is = FileDisplayUtil.class.getClassLoader().getResourceAsStream("imgs/item/file.png");
                iv = new ImageView(new Image(is, imageDisplaySize, imageDisplaySize, true, true));
            }
        }

        return iv;

    }
}
