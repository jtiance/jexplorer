package com.tiance.jexplorer.config;

import java.util.HashMap;
import java.util.Map;

public class PopularFolder {
    private static Map<String, String> priorFolder = new HashMap();
    private static Map<String, String> folderPic = new HashMap();

    static {
        priorFolder.put("Downloads", "下载");
        priorFolder.put("Music", "音乐");
        priorFolder.put("Videos", "视频");
        priorFolder.put("Documents", "文档");
        priorFolder.put("Pictures", "图片");
        priorFolder.put("Desktop", "桌面");

        folderPic.put("Downloads", "imgs/folder/download.png");
        folderPic.put("Music", "imgs/folder/music.png");
        folderPic.put("Videos", "imgs/folder/video.png");
        folderPic.put("Documents", "imgs/folder/doc.png");
        folderPic.put("Pictures", "imgs/folder/pic.png");
        folderPic.put("Desktop", "imgs/folder/desktop.png");
    }

    public static boolean contains(String key) {
        return priorFolder.containsKey(key);
    }

    public static String translate(String key) {
        return priorFolder.get(key);
    }

    public static String getImagePath(String key) {
        return folderPic.get(key);
    }

}
