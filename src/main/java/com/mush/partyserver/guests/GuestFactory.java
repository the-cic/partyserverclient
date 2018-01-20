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
public interface GuestFactory {

    public Guest createNewGuest(String guestName);
}
