/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.command;

import com.mush.partyserver.message.ClientMessage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cic
 */
public class StoreAssetsCommand extends ClientMessage {

    Map<String, String> assets;

    public StoreAssetsCommand() {
        super("storeAssets", "assets", new HashMap<String, String>());
        assets = (Map<String, String>) body.get("assets");
    }

    public void addAsset(String name, String base64) {
        assets.put(name, base64);
    }

}
