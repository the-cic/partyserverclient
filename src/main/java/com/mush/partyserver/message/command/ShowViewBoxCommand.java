/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.command;

import com.mush.partyserver.message.ClientMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cic
 */
public class ShowViewBoxCommand extends ClientMessage {

    List<Map<String, Object>> items;

    public ShowViewBoxCommand(boolean show) {
        super(show ? "showViewBox" : "hideViewBox", show ? "items" : null, new ArrayList<>());
        if (!show) {
            return;
        }
        items = (List<Map<String, Object>>) body.get("items");
    }

    private Map<String, Object> addItem(String id, String contentName, String content) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", id);
        item.put(contentName, content);
        items.add(item);
        return item;
    }

    public void addBackgroundItem(String id, String shape) {
        Map<String, Object> item = addItem(id, "shape", shape);
        item.put("background", Boolean.TRUE);
    }

    public void addSpriteItem(String id, String shape, double x, double y, double width) {
        Map<String, Object> item = addItem(id, "shape", shape);
        item.put("x", Math.round(x * 100.0) * 0.01);
        item.put("y", Math.round(y * 100.0) * 0.01);
        item.put("width", width);
    }

    public void addLabelItem(String id, String text, double x, double y) {
        Map<String, Object> item = addItem(id, "text", text);
        item.put("x", Math.round(x * 100.0) * 0.01);
        item.put("y", Math.round(y * 100.0) * 0.01);
    }

}
