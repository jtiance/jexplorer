package com.tiance.jexplorer.service.impl;

import com.tiance.jexplorer.service.FileService;
import com.tiance.jexplorer.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public void openFile(File file) {

        String mimetype = FileUtil.getMimetype(file);
        logger.info("OPEN FILE, mimetype:{}", mimetype);
        try {
            String absolutePath = file.getAbsolutePath();
            Runtime.getRuntime().exec("deepin-image-viewer --new-window " + absolutePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
