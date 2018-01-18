/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cic
 */
public class AssetLibrary {

    private static final String JPEG_PREFIX = "data:image/jpeg;base64,";
    private static final String PNG_PREFIX = "data:image/png;base64,";

    private final Map<String, AssetDefinition> assets;
    private final Logger logger;

    public AssetLibrary() {
        logger = LogManager.getLogger(this.getClass());
        assets = new HashMap<>();
    }

    public void addAsset(String name, Path path, int width) {
        if (assets.containsKey(name)) {
            return;
        }
        try {
            logger.info("Loading asset {} from {}", name, path);
            String base64 = loadAsset(path);
            String prefix = getBase64Prefix(path);
            assets.put(name, new AssetDefinition(name, prefix + base64, width));
        } catch (IOException ex) {
            logger.error("Failed to load asset: {}", ex.getMessage());
        }
    }

    public Set<String> getNames() {
        return assets.keySet();
    }

    public AssetDefinition getAsset(String name) {
        return assets.get(name);
    }

    private String loadAsset(Path path) throws IOException {
        byte[] binaryData = Files.readAllBytes(path);
        return Base64.encodeBase64String(binaryData);
    }

    private String getBase64Prefix(Path path) {
        if (path.endsWith(".png")) {
            return PNG_PREFIX;
        }
        return JPEG_PREFIX;
    }
}
