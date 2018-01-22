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
public class ShowChoiceCommand extends ClientMessage {
    
    private final Map<String, Object> content;
    private final List<Map<String, String>> fields;
    
    public ShowChoiceCommand(String id, String title) {
        super("showChoice", "choice", new HashMap<>());
        content = (Map<String, Object>) this.body.get("choice");
        content.put("title", title);
        content.put("id", id);
        fields = new ArrayList<>();
        content.put("fields", fields);
    }
    
    public void addChoice(String name, String label) {
        Map<String, String> field = new HashMap<>();
        field.put("name", name);
        field.put("label", label);
        fields.add(field);        
    }
}
