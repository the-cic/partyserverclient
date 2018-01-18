/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import com.mush.partyserver.message.command.ShowViewBoxCommand;
import com.mush.partyserver.message.command.UpdateViewBoxCommand;

/**
 *
 * @author cic
 */
public class ViewBoxItem {

    public final String id;
    public final boolean background;

    public String assetName;
    private int assetWidth = 0;
    private float scale = 1;

    public int x;
    public int y;
    public int width;

    private boolean positionDirty;
    private boolean scaleDirty;
    private boolean assetDirty;

    private ViewBoxItem(String id, boolean background, AssetDefinition asset) {
        this.id = id;
        this.background = background;
        setAsset(asset);
    }

    public static ViewBoxItem createSprite(String id, AssetDefinition asset, int x, int y) {
        ViewBoxItem item = new ViewBoxItem(id, false, asset);
        item.move(x, y);
        return item;
    }

    public static ViewBoxItem createBackground(String id, AssetDefinition asset) {
        return new ViewBoxItem(id, true, asset);
    }

    public boolean isDirty() {
        return positionDirty || scaleDirty || assetDirty;
    }

    public void clearDirty() {
        positionDirty = false;
        scaleDirty = false;
        assetDirty = false;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        positionDirty = true;
    }

    public void scale(float scale) {
        this.scale = scale;
        applyScale();
    }

    private void applyScale() {
        width = (int) (assetWidth * scale);
        scaleDirty = true;
    }

    public void setAsset(AssetDefinition asset) {
        assetName = asset.name;
        assetWidth = asset.width;
        assetDirty = true;
        applyScale();
    }

    public void outputTo(UpdateViewBoxCommand command) {
        if (background) {
            if (assetDirty) {
                command.addBackgroundItem(id, assetName);
            }
        } else if (positionDirty || scaleDirty) {
            command.addSpriteItem(id, assetName, x, y, width);
        }
    }

    public void outputTo(ShowViewBoxCommand command) {
        if (background) {
            command.addBackgroundItem(id, assetName);
        } else {
            command.addSpriteItem(id, assetName, x, y, width);
        }
    }

}
