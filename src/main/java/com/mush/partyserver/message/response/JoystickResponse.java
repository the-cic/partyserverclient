/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.response;

import com.mush.partyserver.message.ServerMessage;

/**
 *
 * @author cic
 */
public class JoystickResponse extends GuestResponse {

    public String joystickDirection = null;

    public JoystickResponse(ServerMessage message) {
        super(message);
        joystickDirection = (String) body.get("joystick");
    }

}
