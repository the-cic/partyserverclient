/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.example;

import com.mush.partyserver.guests.AssetLibrary;
import com.mush.partyserver.guests.Guest;
import com.mush.partyserver.message.command.ShowFormCommand;
import com.mush.partyserver.message.command.StandByCommand;
import com.mush.partyserver.message.command.StoreAssetsCommand;
import com.mush.partyserver.message.ClientMessage;
import com.mush.partyserver.message.command.ShowJoystickCommand;
import com.mush.partyserver.message.response.FormResponse;
import com.mush.partyserver.message.response.GuestResponse;
import com.mush.partyserver.message.response.JoystickResponse;
import com.mush.partyserver.rooms.SimpleRoomOwnerDelegate;
import java.nio.file.Paths;

/**
 *
 * @author cic
 */
public class ExampleDelegate extends SimpleRoomOwnerDelegate {

    private final static String NAME = "ExampleOwner";
    private final static String TOKEN = "405784574057349";

    private final AssetLibrary assets;

    public ExampleDelegate(String host) {
        super(host, NAME, TOKEN);
        assets = new AssetLibrary();

        assets.addAsset("blob", Paths.get("src/main/resources/blob.jpg"), 20);
        assets.addAsset("box", Paths.get("src/main/resources/box.png"), 25);
        assets.addAsset("landscape", Paths.get("src/main/resources/landscape.jpg"), 25);
    }

    @Override
    public void onOpen() {
        logger.info("on open");
    }

    @Override
    public void onClose(String reason) {
        logger.info("on close: {}", reason);
    }

    @Override
    public void onError(Exception ex) {
        logger.info("on error: {}", ex.getMessage());
    }

    @Override
    public void onError(String error, String errorDescription) {
        logger.info("on error: {}, {}", error, errorDescription);
    }

    @Override
    public void onLogin() {
        logger.info("on login");
    }

    @Override
    public void onGuestJoined(Guest guest) {
        sendForm(guest);
    }

    @Override
    public void onGuestLeft(Guest guest) {

    }

    @Override
    public void onGuestResponse(GuestResponse response) {
        logger.info(response.body);
        Guest guest = guests.getGuest(response.from);
        if (response instanceof FormResponse) {
            FormResponse form = (FormResponse) response;
            logger.info("form: {}, {}", form.formId, form.values);
            if ("start".equals(form.values.get("line1"))) {
                sendAssets(guest);
                sendJoystick(guest);
            } else {
                sayHi(guests.getGuest(response.from));
            }
        }
        if (response instanceof JoystickResponse) {
            JoystickResponse joystick = (JoystickResponse) response;
            logger.info("joystick direction: {}", joystick.joystickDirection);
        }
    }

    private void sayHi(Guest guest) {
        ClientMessage message = new StandByCommand("Hello " + guest.getName() + ", plase wait now.");
        message.setRecipient(guest.getName());
        client.sendClientMessage(message);
    }

    private void sendAssets(Guest guest) {
        StoreAssetsCommand message = guest.getStoreMissingAssetsMessage(assets);
        if (message == null) {
            return;
        }
        client.sendClientMessage(message);
    }

    private void sendJoystick(Guest guest) {
        ShowJoystickCommand showJoystick = new ShowJoystickCommand(true, ShowJoystickCommand.DIRECTIONS_4);
        showJoystick.setRecipient(guest.getName());
        client.sendClientMessage(showJoystick);
    }

    private void sendForm(Guest guest) {
        ShowFormCommand message = new ShowFormCommand("form1", "Type some things now");
        message.setRecipient(guest.getName());
        message.addTextField("line1", "type \"start\"");
        message.addTextField("line2", "and also this");
        message.addTextField("line3", "and this");
        client.sendClientMessage(message);
    }

}
