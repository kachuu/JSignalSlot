package com.java.test;

import com.java.local.QObject;

public class Sender extends QObject {

    String tag = "Sender";

    public void trigger() {
        Receiver r = new Receiver();
        connect(this, "signal_1param(Integer)", r, "slot_1param(Integer)");
        connect(this, "signal_1param(Integer)", r, "slot_1param(Integer)");
        connect(this, "signal_2param(Integer, String)", r, "slot_2param(Integer, String)");
        connect(r, "signal_2param(Integer, String)", this, "slot_2param(Integer, String)");

        emit("signal_1param(Integer)",
            1000);
        emit("signal_2param(Integer, String)",
            1000, "A");

        System.out.println("----------");

        // disconnect(this, "signal_1param(Integer)", null, "");
        disconnect(this, "signal_1param(Integer)", r, "slot_1param(Integer)");
        // disconnect(this, "signal_2param(Integer, String)", r, "slot_2param(Integer, String)");

        emit("signal_1param(Integer)",
            1000);
        emit("signal_2param(Integer, String)",
            1000, "A");
    }

    public void slot_2param(Integer v, String s) {
        System.out.println(tag + ":slot_2param(" + v + ", " + s + ")");
    }
}
