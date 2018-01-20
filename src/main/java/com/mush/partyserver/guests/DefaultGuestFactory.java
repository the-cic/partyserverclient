/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.guests;

/**
 *
 * @author cic
 */
public class DefaultGuestFactory implements GuestFactory {

    @Override
    public Guest createNewGuest(String guestName) {
        return new Guest(guestName);
    }

}
