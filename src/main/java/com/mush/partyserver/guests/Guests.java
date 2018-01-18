/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cic
 */
public class Guests {

    private final Map<String, Guest> guests;

    public Guests() {
        guests = new HashMap<>();
    }

    public Guest addGuest(String guestName) {
        Guest guest = new Guest(guestName);
        guests.put(guestName, guest);
        return guest;
    }
    
    public Guest getGuest(String guestName) {
        return guests.get(guestName);
    }
}
