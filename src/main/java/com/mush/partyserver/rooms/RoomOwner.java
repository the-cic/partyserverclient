/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.rooms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mush.partyserver.rooms.message.ClientMessage;
import com.mush.partyserver.rooms.message.LoginMessage;
import com.mush.partyserver.rooms.message.Message;
import com.mush.partyserver.rooms.message.ServerMessage;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
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
    
    public void sendClientMessage(List<String> recipients, String action, String contentName, Object content) {
        ClientMessage message = new ClientMessage();
        message.recipients = recipients;
        message.subject = ClientMessage.SUBJECT_COMMAND;
        message.body = new HashMap<>();
        message.body.put(ClientMessage.BODY_ACTION, action);
        message.body.put(contentName, content);
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
                default:
                    delegate.onMessage(serverMessage);
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
