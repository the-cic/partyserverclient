/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mush.partyserver.example;

/**
 *
 * @author cic
 */
public class Example {

    public static void main(String[] args) {

        String host = "ws://localhost:8182";

        ExampleDelegate delegate = new ExampleDelegate(host);
        delegate.connect();
    }
}
