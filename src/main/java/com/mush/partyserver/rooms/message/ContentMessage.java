/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.rooms.message;

import java.util.Map;

/**
 * Base message with subject and content
 * 
 * @author cic
 */
public class ContentMessage extends Message {

    public String subject;

    public Map<String, Object> body;
}
