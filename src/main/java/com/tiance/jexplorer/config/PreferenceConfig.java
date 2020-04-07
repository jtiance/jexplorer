package com.tiance.jexplorer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "preference")
public class PreferenceConfig {

    /**
     * 是否显示隐藏文件或文件夹
     */
    private boolean showHidden;

    private int fileDisplayStyle; //1: 列表; 2: 块

    private int fileDisplaySize; // 显示大小, 1,2,3,4,5

    public boolean isShowHidden() {
        return showHidden;
    }

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public int getFileDisplayStyle() {
        return fileDisplayStyle;
    }

    public void setFileDisplayStyle(int fileDisplayStyle) {
        this.fileDisplayStyle = fileDisplayStyle;
    }

    public int getFileDisplaySize() {
        return fileDisplaySize;
    }

    public void setFileDisplaySize(int fileDisplaySize) {
        this.fileDisplaySize = fileDisplaySize;
    }
}
