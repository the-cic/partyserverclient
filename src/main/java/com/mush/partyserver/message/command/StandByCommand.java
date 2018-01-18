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
public class StandByCommand extends ClientMessage {
    
    public StandByCommand(String text) {
        super("standBy", "text", text);
    }
}
