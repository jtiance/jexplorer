package com.tiance.jexplorer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "size")
public class SizeConfig {

    private boolean stageMaximized;

    private int stageWidth;

    private int stageHeight;

    public boolean isStageMaximized() {
        return stageMaximized;
    }

    public void setStageMaximized(boolean stageMaximized) {
        this.stageMaximized = stageMaximized;
    }

    public int getStageWidth() {
        return stageWidth;
    }

    public void setStageWidth(int stageWidth) {
        this.stageWidth = stageWidth;
    }

    public int getStageHeight() {
        return stageHeight;
    }

    public void setStageHeight(int stageHeight) {
        this.stageHeight = stageHeight;
    }
}
