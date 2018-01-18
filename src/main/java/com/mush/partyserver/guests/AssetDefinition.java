/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

/**
 *
 * @author cic
 */
public class AssetDefinition {

    public final String name;
    public final String base64;
    public final int width;

    public AssetDefinition(String name, String base64, int width){
        this.name = name;
        this.base64 = base64;
        this.width = width;
    }

}
