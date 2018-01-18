/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.rooms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mush.partyserver.message.ClientMessage;
import com.mush.partyserver.message.LoginMessage;
import com.mush.partyserver.message.Message;
import com.mush.partyserver.message.ServerMessage;
import com.mush.partyserver.message.response.GuestResponse;
import java.io.IOException;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author cic
 */
public class RoomOwner extends WebSocketClient {

    private final Logger logger;
    private final RoomOwnerDelegate delegate;

    public RoomOwner(URI serverUri, RoomOwnerDelegate delegate0) {
        super(serverUri, new Draft_6455());
        delegate = delegate0;
        logger = LogManager.getLogger(this.getClass());
    }

    private void send(Message message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String loginJson = mapper.writeValueAsString(message);
            send(loginJson);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    public void sendClientMessage(ClientMessage message) {
        send(message);
    }

    @Override
    public void onOpen(ServerHandshake sh) {
        logger.info("Connected");
        delegate.onOpen();
        LoginMessage login = delegate.getLoginMessage();
        send(login);
    }

    @Override
    public void onMessage(String message) {
        logger.info("Message: {}", message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ServerMessage serverMessage = mapper.readValue(message, ServerMessage.class);

            switch (serverMessage.subject) {
                case ServerMessage.SUBJECT_ERROR:
                    if (serverMessage.body != null) {
                        delegate.onError(
                                (String) serverMessage.body.get(ServerMessage.BODY_ERROR),
                                (String) serverMessage.body.get(ServerMessage.BODY_ERROR_DESCRIPTION));
                    } else {
                        delegate.onError(null, null);
                    }
                    break;
                case ServerMessage.SUBJECT_LOGIN_ACCEPTED:
                    delegate.onLogin();
                    break;
                case ServerMessage.SUBJECT_USER_RESPONSE:
                    delegate.onGuestResponse(GuestResponse.toGuestResponse(serverMessage));
                    break;
                default:
                    delegate.onServerMessage(serverMessage);
            }
        } catch (IOException ex) {
            delegate.onError(ex);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Disconnected: {}", reason);
        delegate.onClose(reason);
    }

    @Override
    public void onError(Exception ex) {
        logger.error(ex.getMessage());
        delegate.onError(ex);
    }

}
