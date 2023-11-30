package com.java.test;

import com.java.local.QObject;

public class Receiver extends QObject {
    
    String tag = "Receiver";

    public void slot_1param(Integer v) {
        System.out.println(tag + ":slot_1param(" + v + ")");
    }

    public void slot_2param(Integer v, String s) {
        System.out.println(tag + ":slot_2param(" + v + ", " + s + ")");

        emit("signal_2param(Integer, String)",
            v + 1, s + "B");
    }
}
