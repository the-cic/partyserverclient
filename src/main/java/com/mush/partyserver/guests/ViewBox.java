/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import com.mush.partyserver.message.command.ShowViewBoxCommand;
import com.mush.partyserver.message.command.UpdateViewBoxCommand;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cic
 */
public class ViewBox {

    private final Map<String, ViewBoxItem> sprites;
    private final Map<String, ViewBoxItem> backgrounds;

    public ViewBox() {
        sprites = new HashMap<>();
        backgrounds = new HashMap<>();
    }

    public void clear() {
        sprites.clear();
        backgrounds.clear();
    }

    public void addItem(ViewBoxItem item) {
        if (item.background) {
            backgrounds.put(item.id, item);
        } else {
            sprites.put(item.id, item);
        }
    }

    public ViewBoxItem getSprite(String id) {
        return sprites.get(id);
    }

    public ViewBoxItem getBackground(String id) {
        return backgrounds.get(id);
    }

    public void outputTo(ShowViewBoxCommand command) {
        for (ViewBoxItem item : backgrounds.values()) {
            item.outputTo(command);
        }
        for (ViewBoxItem item : sprites.values()) {
            item.outputTo(command);
        }
    }

    public void outputTo(UpdateViewBoxCommand command) {
        for (ViewBoxItem item : backgrounds.values()) {
            item.outputTo(command);
        }
        for (ViewBoxItem item : sprites.values()) {
            item.outputTo(command);
        }
    }

}
