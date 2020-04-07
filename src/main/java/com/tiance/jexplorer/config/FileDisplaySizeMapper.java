package com.tiance.jexplorer.config;

import java.util.HashMap;
import java.util.Map;

public class FileDisplaySizeMapper {

    private static Map<Integer, Double> blockMap = new HashMap<>();

    static {
        blockMap.put(1, 80d);
        blockMap.put(2, 110d);
        blockMap.put(3, 140d);
        blockMap.put(4, 170d);
        blockMap.put(5, 200d);
    }

    public static Double getBlockSize(int key) {
        Double aDouble = blockMap.get(key);
        if (aDouble == null) {
            return blockMap.get(3);
        }

        return aDouble;
    }
}
