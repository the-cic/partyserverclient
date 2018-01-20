/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.response;

import com.mush.partyserver.message.ServerMessage;
import java.util.List;

/**
 *
 * @author cic
 */
public class JoystickResponse extends GuestResponse {

    public double directionX = 0;
    public double directionY = 0;

    public JoystickResponse(ServerMessage message) {
        super(message);
        directionX = 0;
        directionY = 0;
        try {
            List<Number> joystick = (List) body.get("joystick");
            Number x = joystick.get(0);
            Number y = joystick.get(1);
            directionX = x != null ? x.doubleValue() : 0;
            directionY = y != null ? y.doubleValue() : 0;
        } catch (Exception e) {
        }
    }

}
