/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.response;

import com.mush.partyserver.message.ServerMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cic
 */
public class GuestResponse extends ServerMessage {

    public GuestResponse(ServerMessage message) {
        this.body = message.body;
        this.from = message.from;
        this.subject = message.subject;
    }

    public static GuestResponse toGuestResponse(ServerMessage message) {
        GuestResponse response;
        if (message.body.containsKey("type")) {
            try {
                switch ((String) message.body.get("type")) {
                    case "form":
                        response = new FormResponse(message);
                        break;
                    case "choice":
                        response = new ChoiceResponse(message);
                        break;
                    case "joystick":
                        response = new JoystickResponse(message);
                        break;
                    default:
                        response = new GuestResponse(message);
                }
            } catch (Exception ex) {
                Logger logger = LogManager.getLogger(GuestResponse.class);
                logger.error("Error converting GuestResponse: {}", ex.getMessage());
                response = new GuestResponse(message);
            }
        } else {
            response = new GuestResponse(message);
        }
        return response;
    }

}
