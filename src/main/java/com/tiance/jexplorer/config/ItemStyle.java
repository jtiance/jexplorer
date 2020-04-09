package com.tiance.jexplorer.config;

import java.util.HashMap;
import java.util.Map;

public class ItemStyle {
    private static Map<String, String> folderPic = new HashMap();

    static {
        folderPic.put("generalFolder", "imgs/item/folder.png");
        folderPic.put("/", "imgs/item/disk1.png");
        folderPic.put("internalDisk", "imgs/item/disk1.png");
        folderPic.put("externalDisk", "imgs/item/disk2.png");
        folderPic.put("Downloads", "imgs/item/home_download.png");
        folderPic.put("Music", "imgs/item/home_music.png");
        folderPic.put("Videos", "imgs/item/home_video.png");
        folderPic.put("Documents", "imgs/item/home_doc.png");
        folderPic.put("Pictures", "imgs/item/home_pic.png");
        folderPic.put("Desktop", "imgs/item/home_desktop.png");
    }

    public static String getImagePath(String key) {
        String s = folderPic.get(key);
        if (s == null) {
            s = folderPic.get("generalFolder");
        }
        return s;
    }

}
