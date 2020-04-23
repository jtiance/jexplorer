package com.tiance.jexplorer.service.impl;

import com.tiance.jexplorer.layout.NavigationBar;
import com.tiance.jexplorer.layout.NavigationPathBar;
import com.tiance.jexplorer.service.TerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Lazy
public class TerminalServiceImpl implements TerminalService {

    private Logger logger = LoggerFactory.getLogger(TerminalServiceImpl.class);

    @Autowired
    private NavigationBar navigationBar;

    @Override
    public void openTerminal() {
        NavigationPathBar curNavigationPathBar = navigationBar.getCurNavigationPathBar();
        String path = curNavigationPathBar.getPath();
        try {
            Runtime.getRuntime().exec("  deepin-terminal -m normal &", null, new File(path));
        } catch (IOException e) {
            logger.info("Error opening folder: {}", path, e);
        }
    }
}
