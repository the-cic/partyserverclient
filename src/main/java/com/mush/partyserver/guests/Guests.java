/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author cic
 */
public class Guests {

    private final Map<String, Guest> guests;
    private final GuestFactory factory;

    public Guests(GuestFactory guestFactory) {
        guests = new HashMap<>();
        factory = guestFactory;
    }

    public Guest addGuest(String guestName) {
        Guest guest = factory.createNewGuest(guestName);
        guests.put(guestName, guest);
        return guest;
    }

    public Guest getGuest(String guestName) {
        return guests.get(guestName);
    }

    public Guest removeGuest(String guestName) {
        return guests.remove(guestName);
    }

    public Set<Guest> getGuestsWithProperty(String key, Object value) {
        Set<Guest> matching = new HashSet<>();
        for (Guest guest : guests.values()) {
            if (key == null || Objects.equals(guest.getProperty(key), value)) {
                matching.add(guest);
            }
        }
        return matching;
    }
    
    public Set<String> getGuestNamesWithProperty(String key, Object value) {
        Set<Guest> guestSet = getGuestsWithProperty(key, value);
        Set<String> nameSet = new HashSet<>();
        for (Guest guest : guestSet) {
            nameSet.add(guest.getName());
        }
        return nameSet;
    }
    
}
