/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.rooms;

import com.mush.partyserver.message.LoginMessage;
import com.mush.partyserver.message.ServerMessage;
import com.mush.partyserver.message.response.GuestResponse;

/**
 *
 * @author cic
 */
public abstract class RoomOwnerDelegate {

    private final String name;
    private final String token;
    private final String preferredRoom;

    public RoomOwnerDelegate(String name, String token, String preferredRoom) {
        this.name = name;
        this.token = token;
        this.preferredRoom = preferredRoom;
    }

    LoginMessage getLoginMessage() {
        LoginMessage message = new LoginMessage();
        message.login = name;
        message.token = token;
        message.room = preferredRoom;
        return message;
    }

    public abstract void onOpen();

    public abstract void onClose(String reason);

    public abstract void onError(Exception ex);

    public abstract void onError(String error, String errorDescription);

    public abstract void onLogin();

    public abstract void onServerMessage(ServerMessage serverMessage);

    public abstract void onGuestResponse(GuestResponse guestResponse);

}
