/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.rooms.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Message sent to a list of guests
 * 
 * @author cic
 */
public class ClientMessage extends ContentMessage {
    
    public static final String SUBJECT_COMMAND = "command";
    
    public static final String BODY_ACTION = "action";

    @JsonProperty(value = "to")
    public List<String> recipients;

}
