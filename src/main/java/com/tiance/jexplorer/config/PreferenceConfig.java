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

    public boolean isShowHidden() {
        return showHidden;
    }

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }
}
