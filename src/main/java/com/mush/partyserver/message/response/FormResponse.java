/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.response;

import com.mush.partyserver.message.ServerMessage;
import java.util.Map;

/**
 *
 * @author cic
 */
public class FormResponse extends GuestResponse {

    public String formId = null;
    public Map<String, String> values;

    public FormResponse(ServerMessage message) {
        super(message);
        Map<String, Object> form = (Map<String, Object>) body.get("form");
        formId = (String) form.get("id");
        values = (Map<String, String>) form.get("values");
    }
}
