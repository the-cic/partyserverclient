/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.message.command;

import com.mush.partyserver.message.ClientMessage;

/**
 *
 * @author cic
 */
public class ShowJoystickCommand extends ClientMessage {
    
    public static final int DIRECTIONS_4 = 4;
    public static final int DIRECTIONS_8 = 8;

    public ShowJoystickCommand(boolean show, Integer directions) {
        super(
                show ? "showJoystick" : "hideJoystick",
                show ? "directions" : null,
                directions);
    }

}
