package com.tiance.jexplorer.service.impl;

import com.tiance.jexplorer.service.ApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileFilter;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final String SHARE_APPLICATION_PATH = "/usr/share/application";

    @PostConstruct
    public void initShareApplications() {
        File folder = new File(SHARE_APPLICATION_PATH);
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".desktop");
            }
        });
    }
}
