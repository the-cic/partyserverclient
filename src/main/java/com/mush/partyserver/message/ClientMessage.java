/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Message sent to a list of guests
 *
 * @author cic
 */
public class ClientMessage extends ContentMessage {

    public static final String SUBJECT_COMMAND = "command";

    public static final String BODY_ACTION = "action";

    @JsonProperty(value = "to")
    public Collection<String> recipients;

    public ClientMessage(String action, String contentName, Object content) {
        this.subject = SUBJECT_COMMAND;
        this.body = new HashMap<>();
        this.body.put(ClientMessage.BODY_ACTION, action);
        if (contentName != null) {
            this.body.put(contentName, content);
        }
    }

    public void setRecipients(String[] array) {
        recipients = Arrays.asList(array);
    }
    
    public void setRecipients(Collection<String> names) {
        this.recipients = names;
    }

    public void setRecipient(String name) {
        recipients = Arrays.asList(new String[]{name});
    }

}
