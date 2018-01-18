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
public class ShowFormCommand extends ClientMessage {

    private final Map<String, Object> formContent;
    private final List<Map<String, String>> formFields;

    public ShowFormCommand(String id, String title) {
        super("showForm", "form", new HashMap<>());
        formContent = (Map<String, Object>) this.body.get("form");
        formContent.put("title", title);
        formContent.put("id", id);
        formFields = new ArrayList<>();
        formContent.put("fields", formFields);
    }

    private void addField(String type, String name, String label) {
        Map<String, String> field = new HashMap<>();
        field.put("type", type);
        field.put("name", name);
        field.put("label", label);
        formFields.add(field);
    }

    public void addTextField(String name, String label) {
        addField("text", name, label);
    }

}
