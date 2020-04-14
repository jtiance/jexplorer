package com.tiance.jexplorer.util;

import net.sf.jmimemagic.*;

import java.io.File;

public class FileUtil {

    private static Magic magic = new Magic();

    public static String getMimetype(File file) {
        try {
            MagicMatch magicMatch = magic.getMagicMatch(file, true);
            return magicMatch.getMimeType();
        } catch (MagicParseException e) {
            e.printStackTrace();
        } catch (MagicMatchNotFoundException e) {
            e.printStackTrace();
        } catch (MagicException e) {
            e.printStackTrace();
        }

        return "";
    }
}
