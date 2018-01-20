/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.rooms;

import com.mush.partyserver.guests.DefaultGuestFactory;
import com.mush.partyserver.guests.Guest;
import com.mush.partyserver.guests.Guests;
import com.mush.partyserver.message.ServerMessage;
import com.mush.partyserver.message.response.GuestResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cic
 */
public abstract class SimpleRoomOwnerDelegate extends RoomOwnerDelegate {

    protected final Logger logger;
    protected final RoomOwner client;
    protected final Guests guests;
    private String roomName = null;

    public SimpleRoomOwnerDelegate(String host, String name, String token) {
        super(name, token);
        logger = LogManager.getLogger(this.getClass());

        URI hostUri = null;
        try {
            hostUri = new URI(host);
        } catch (URISyntaxException ex) {
        }

        client = new RoomOwner(hostUri, this);
        guests = new Guests(new DefaultGuestFactory());
    }

    public void connect() {
        client.connect();
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public void onServerMessage(ServerMessage serverMessage) {
        try {
            Guest guest;
            String guestName;
            switch (serverMessage.subject) {
                case ServerMessage.SUBJECT_ROOM_CREATED:
                    roomName = (String) serverMessage.body.get(ServerMessage.BODY_ROOM_NAME);
                    logger.info("I has a room: {}", roomName);
                    break;
                case ServerMessage.SUBJECT_USER_CONNECTED:
                    guestName = (String) serverMessage.body.get(ServerMessage.BODY_GUEST_NAME);
                    guest = guests.addGuest(guestName);
                    onGuestJoined(guest);
                    break;
                case ServerMessage.SUBJECT_USER_DISCONNECTED:
                    guestName = (String) serverMessage.body.get(ServerMessage.BODY_GUEST_NAME);
                    guest = guests.removeGuest(guestName);
                    onGuestLeft(guest);
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public abstract void onGuestJoined(Guest guest);

    public abstract void onGuestLeft(Guest guest);

    @Override
    public void onGuestResponse(GuestResponse response) {
        Guest guest = guests.getGuest(response.from);
        guest.onGuestResponse(response);
    }

}
