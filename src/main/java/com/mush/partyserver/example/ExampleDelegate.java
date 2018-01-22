/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.example;

import com.mush.partyserver.guests.AssetDefinition;
import com.mush.partyserver.guests.AssetLibrary;
import com.mush.partyserver.guests.Guest;
import com.mush.partyserver.guests.ViewBox;
import com.mush.partyserver.guests.ViewBoxItem;
import com.mush.partyserver.message.command.ShowFormCommand;
import com.mush.partyserver.message.command.StandByCommand;
import com.mush.partyserver.message.command.StoreAssetsCommand;
import com.mush.partyserver.message.ClientMessage;
import com.mush.partyserver.message.command.ShowChoiceCommand;
import com.mush.partyserver.message.command.ShowJoystickCommand;
import com.mush.partyserver.message.command.ShowViewBoxCommand;
import com.mush.partyserver.message.command.UpdateViewBoxCommand;
import com.mush.partyserver.message.response.ChoiceResponse;
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
    private final ViewBox viewBox;

    public ExampleDelegate(String host) {
        super(host, NAME, TOKEN);
        assets = new AssetLibrary();
        viewBox = new ViewBox();

        assets.addAsset("blob", Paths.get("src/main/resources/example/blob.jpg"), 20, 20);
        assets.addAsset("box", Paths.get("src/main/resources/example/box.png"), 25, 25);
        assets.addAsset("landscape", Paths.get("src/main/resources/example/landscape.jpg"), 25, 10);

        AssetDefinition bg = assets.getAsset("landscape");

        viewBox.setSize(bg.width, bg.height);
    }

    @Override
    public void onOpen() {
        logger.info("on open");

        ViewBoxItem bg = ViewBoxItem.createBackground("bg", assets.getAsset("landscape"));
        viewBox.addItem(bg);
    }

    @Override
    public void onClose(String reason) {
        logger.info("on close: {}", reason);
        viewBox.clear();
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
        int x = (int) (20 + Math.random() * 60);
        int y = (int) (20 + Math.random() * 60);
        ViewBoxItem sprite = ViewBoxItem.createSprite(
                "guest_" + guest.getName(),
                assets.getAsset("box"),
                x, y);
        sprite.scale(0.2f);
        viewBox.addItem(sprite);
        guest.setProperty("x", x);
        guest.setProperty("y", y);
        sendChoice(guest);
        sendResetViewBoxToAll();
    }

    @Override
    public void onGuestLeft(Guest guest) {
        guest.setProperty("view", false);
        ViewBoxItem sprite = viewBox.getSprite("guest_" + guest.getName());
        viewBox.removeItem(sprite);
        sendResetViewBoxToAll();
    }

    @Override
    public void onGuestResponse(GuestResponse response) {
        logger.info(response.body);
        Guest guest = guests.getGuest(response.from);
        if (response instanceof ChoiceResponse) {
            ChoiceResponse choice = (ChoiceResponse) response;
            logger.info("choice: {}, {}", choice.choiceId, choice.value);
            String selected = choice.value;
            switch (selected) {
                case "form":
                    sendForm(guest);
                    break;
                case "viewBox":
                    sendAssets(guest);
                    sendShowViewBox(guest);
                    sendJoystick(guest);
                    break;
                case "wait":
                    sayHi(guest, null);
                    break;
            }
        }
        if (response instanceof FormResponse) {
            FormResponse form = (FormResponse) response;
            logger.info("form: {}, {}", form.formId, form.values);
            sayHi(guest, form.values.get("line1"));
        }
        if (response instanceof JoystickResponse) {
            JoystickResponse joystick = (JoystickResponse) response;
            logger.info("joystick direction: {}, {}", joystick.directionX, joystick.directionY);
            double x = guest.getIntegerProperty("x");
            double y = guest.getIntegerProperty("y");
            x += joystick.directionX * viewBox.getXMoveFactor();
            y += joystick.directionY * viewBox.getYMoveFactor();
            guest.setProperty("x", (int) x);
            guest.setProperty("y", (int) y);
            ViewBoxItem sprite = viewBox.getSprite("guest_" + guest.getName());
            sprite.move((int) x, (int) y);
            sprite.setAsset(assets.getAsset(Math.random() > 0.5 ? "box" : "blob"));
            sendUpdateViewBoxToAll();
            sendJoystick(guest);
        }
    }

    private void sayHi(Guest guest, String text) {
        String messageText = "Hello " + guest.getName() + ", plase wait now.";
        if (text != null) {
            messageText += " You typed: " + text;
        }
        ClientMessage message = new StandByCommand(messageText);
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

    private void sendShowViewBox(Guest guest) {
        ShowViewBoxCommand command = new ShowViewBoxCommand(true);
        viewBox.outputTo(command);
        command.setRecipient(guest.getName());
        client.sendClientMessage(command);
        guest.setProperty("view", true);
    }

    private void sendJoystick(Guest guest) {
        ShowJoystickCommand showJoystick = new ShowJoystickCommand(true, ShowJoystickCommand.DIRECTIONS_4);
        showJoystick.setRecipient(guest.getName());
        client.sendClientMessage(showJoystick);
    }

    private void sendChoice(Guest guest) {
        ShowChoiceCommand message = new ShowChoiceCommand("choice1", "Choose now!");
        message.setRecipient(guest.getName());
        message.addChoice("viewBox", "view box example");
        message.addChoice("form", "form example");
        message.addChoice("wait", "wait example");
        client.sendClientMessage(message);
    }

    private void sendForm(Guest guest) {
        ShowFormCommand message = new ShowFormCommand("form1", "Type some things now", "Go!");
        message.setRecipient(guest.getName());
        message.addTextField("line1", "type \"view\" or \"choice\"");
        message.addTextField("line2", "and also this");
        message.addTextField("line3", "and this");
        client.sendClientMessage(message);
    }

    private void sendResetViewBoxToAll() {
        ShowViewBoxCommand command = new ShowViewBoxCommand(true);
        viewBox.outputTo(command);
        command.setRecipients(guests.getGuestNamesWithProperty("view", true));
        client.sendClientMessage(command);
    }

    private void sendUpdateViewBoxToAll() {
        UpdateViewBoxCommand command = new UpdateViewBoxCommand();
        viewBox.outputTo(command);
        viewBox.clearDirty();
        command.setRecipients(guests.getGuestNamesWithProperty("view", true));
        client.sendClientMessage(command);
    }

}
