/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message sent by server - could be system related or from a guest
 *
 * @author cic
 */
public class ServerMessage extends ContentMessage {

    public static final String SUBJECT_LOGIN_ACCEPTED = "loginAccepted";
    public static final String SUBJECT_ROOM_CREATED = "roomCreated";
    public static final String SUBJECT_USER_CONNECTED = "userConnected";
    public static final String SUBJECT_USER_DISCONNECTED = "userDisconnected";
    public static final String SUBJECT_ERROR = "error";
    public static final String SUBJECT_USER_RESPONSE = "userResponse";

    public static final String BODY_ERROR = "error";
    public static final String BODY_ERROR_DESCRIPTION = "errorDescription";
    public static final String BODY_ROOM_NAME = "name";
    public static final String BODY_GUEST_NAME = "name";

    @JsonProperty(required = false)
    public String from;
}
