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

    public enum ItemType {
        SPRITE,
        BACKGROUND,
        LABEL
    }

    public final String id;
    public final ItemType type;

    public String asset;
    private int assetWidth = 0;
    private float scale = 1;

    public double x;
    public double y;
    public double width;

    private boolean positionDirty;
    private boolean scaleDirty;
    private boolean assetDirty;

    private ViewBoxItem(String id, boolean background, AssetDefinition asset) {
        this.id = id;
        this.type = background ? ItemType.BACKGROUND : ItemType.SPRITE;
        setAsset(asset);
    }

    private ViewBoxItem(String id, String text) {
        this.id = id;
        this.type = ItemType.LABEL;
        setText(text);
    }

    public static ViewBoxItem createSprite(String id, AssetDefinition asset, double x, double y) {
        ViewBoxItem item = new ViewBoxItem(id, false, asset);
        item.move(x, y);
        return item;
    }

    public static ViewBoxItem createBackground(String id, AssetDefinition asset) {
        return new ViewBoxItem(id, true, asset);
    }

    public static ViewBoxItem createLabel(String id, String text, double x, double y) {
        ViewBoxItem item = new ViewBoxItem(id, text);
        item.move(x, y);
        return item;
    }

    public boolean isDirty() {
        return positionDirty || scaleDirty || assetDirty;
    }

    public void clearDirty() {
        positionDirty = false;
        scaleDirty = false;
        assetDirty = false;
    }

    public void move(double x, double y) {
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

    public void setAsset(AssetDefinition assetDefinition) {
        asset = assetDefinition.name;
        assetWidth = assetDefinition.width;
        assetDirty = true;
        applyScale();
    }

    public void setText(String text) {
        asset = text;
        assetDirty = true;
    }

    public void outputTo(UpdateViewBoxCommand command) {
        switch (type) {
            case BACKGROUND:
                if (assetDirty) {
                    command.addBackgroundItem(id, asset);
                }
                break;
            case SPRITE:
                if (positionDirty || scaleDirty) {
                    command.addSpriteItem(id, asset, x, y, width);
                }
                break;
            case LABEL:
                if (positionDirty || assetDirty) {
                    command.addLabelItem(id, asset, x, y);
                }
                break;
        }
    }

    public void outputTo(ShowViewBoxCommand command) {
        switch (type) {
            case BACKGROUND:
                command.addBackgroundItem(id, asset);
                break;
            case SPRITE:
                command.addSpriteItem(id, asset, x, y, width);
                break;
            case LABEL:
                command.addLabelItem(id, asset, x, y);
                break;
        }
    }

}
