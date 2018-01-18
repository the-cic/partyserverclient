/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import com.mush.partyserver.message.command.StoreAssetsCommand;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cic
 */
public class Guest {

    private final Set<String> sentAssets;
    private final String name;

    public Guest(String name) {
        this.name = name;
        sentAssets = new HashSet<>();
    }

    public String getName() {
        return name;
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
}
