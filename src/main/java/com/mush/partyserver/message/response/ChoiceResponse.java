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
public class ChoiceResponse extends GuestResponse {

    public String choiceId = null;
    public String value;

    public ChoiceResponse(ServerMessage message) {
        super(message);
        Map<String, Object> choice = (Map<String, Object>) body.get("choice");
        choiceId = (String) choice.get("id");
        value = (String) choice.get("value");
    }

}
