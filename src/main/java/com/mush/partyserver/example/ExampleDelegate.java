/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.example;

import com.mush.partyserver.rooms.RoomOwner;
import com.mush.partyserver.rooms.RoomOwnerDelegate;
import com.mush.partyserver.rooms.message.ServerMessage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cic
 */
public class ExampleDelegate extends RoomOwnerDelegate {

    private final static String NAME = "ExampleOwner";
    private final static String TOKEN = "405784574057349";

    private final Logger logger;
    private final RoomOwner client;

    public ExampleDelegate(String host) {
        super(NAME, TOKEN);
        logger = LogManager.getLogger(this.getClass());

        URI hostUri = null;
        try {
            hostUri = new URI(host);
        } catch (URISyntaxException ex) {
        }

        client = new RoomOwner(hostUri, this);
    }

    public void connect() {
        client.connect();
    }

    @Override
    public void onOpen() {
        logger.info("on open");
    }

    @Override
    public void onClose(String reason) {
        logger.info("on close: {}", reason);
    }

    @Override
    public void onError(Exception ex) {
        logger.info("on error: {}", ex.getMessage());
    }

    @Override
    public void onError(String error, String errorDescription) {
        logger.info("on error: {}, {}", error, errorDescription);
    }

    @Override
    public void onLogin() {
        logger.info("on login");
    }

    @Override
    public void onMessage(ServerMessage serverMessage) {
        try {
            switch (serverMessage.subject) {
                case ServerMessage.SUBJECT_ROOM_CREATED:
                    logger.info("I has a room: {}", serverMessage.body.get(ServerMessage.BODY_ROOM_NAME));
                    break;
                case ServerMessage.SUBJECT_USER_CONNECTED:
                    sayHi((String) serverMessage.body.get(ServerMessage.BODY_GUEST_NAME));
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void sayHi(String guest) {
        String[] recipients = new String[]{guest};
        client.sendClientMessage(Arrays.asList(recipients), "standBy", "text", "Hello " + guest + ", plase wait.");
    }

}
