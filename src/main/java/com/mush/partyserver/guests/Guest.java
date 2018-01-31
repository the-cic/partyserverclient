/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import com.mush.partyserver.message.command.StoreAssetsCommand;
import com.mush.partyserver.message.response.GuestResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cic
 */
public class Guest {

    private final Set<String> sentAssets;
    private final String name;
    private final Map<String, Object> properties;

    public Guest(String name) {
        this.name = name;
        sentAssets = new HashSet<>();
        properties = new HashMap<>();
    }

    public String getName() {
        return name;
    }
    
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
    
    public Integer getIntegerProperty(String key) {
        Object value = properties.get(key);
        return value instanceof Integer ? (Integer)value : null;
    }
    
    public Double getDoubleProperty(String key) {
        Object value = properties.get(key);
        return value instanceof Double ? (Double)value : null;
    }
    
    public Boolean getBooleanProperty(String key) {
        Object value = properties.get(key);
        return value instanceof Boolean ? (Boolean)value : null;
    }
    
    public Object getProperty(String key) {
        return properties.get(key);
    }

    public StoreAssetsCommand getStoreMissingAssetsMessage(AssetLibrary library) {
        Set<String> missing = new HashSet<>();
        for (String assetName : library.getNames()) {
            if (!sentAssets.contains(assetName)) {
                missing.add(assetName);
            }
        }
        if (missing.isEmpty()) {
            return null;
        }
        StoreAssetsCommand command = new StoreAssetsCommand();
        command.setRecipients(new String[]{this.name});
        for (String assetName : missing) {
            command.addAsset(assetName, library.getAsset(assetName).base64);
        }
        sentAssets.addAll(missing);
        return command;
    }
    
    public void onGuestResponse(GuestResponse response) {
        // extend if needed
    }
}
