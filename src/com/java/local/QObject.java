package com.java.local;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class QObject {
    
    class ReceiverSlot {
        ReceiverSlot(Object receiver, Method slot, String sign) {
            this.receiver = receiver;
            this.slot = slot;
            this.sign = sign;
        }
        Object receiver;
        Method slot;
        String sign;
    }
    Map<String, List<ReceiverSlot>> signalSlot = new HashMap<String, List<ReceiverSlot>>();

    public static void connect(QObject sender, String signal, QObject receiver, String slot) {
        try {
            String signalSign = signal.replaceAll("\\(|\\)|,| ", "");
            List<ReceiverSlot> receiverSlotList = sender.signalSlot.get(signalSign);
            if (null == receiverSlotList) {
                receiverSlotList = new ArrayList<ReceiverSlot>();
                sender.signalSlot.put(signalSign, receiverSlotList);
            }

            String slotSign = slot.replaceAll("\\(|\\)|,| ", "");
            for (Method method : receiver.getClass().getMethods()) {
                String functionSign = method.getName();
                for (Class<?> param : method.getParameterTypes()) {
                    functionSign += param.getSimpleName();
                }

                if (functionSign.equals(slotSign)) {
                    receiverSlotList.add(sender.new ReceiverSlot(receiver, method, slotSign));
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void disconnect(QObject sender, String signal, QObject receiver, String slot) {
        try {
            String signalSign = signal.replaceAll("\\(|\\)|,| ", "");
            List<ReceiverSlot> receiverSlotList = sender.signalSlot.get(signalSign);
            if (null == receiverSlotList) return;
            if (null == receiver) {
                receiverSlotList.clear();
            } else {
                String slotSign = slot.replaceAll("\\(|\\)|,| ", "");
                for (Iterator<ReceiverSlot> it = receiverSlotList.iterator(); it.hasNext();) {
                    ReceiverSlot receiverSlot = it.next();
                    if (!receiverSlot.receiver.equals(receiver) ||
                        !receiverSlot.sign.equals(slotSign)) continue;
                        it.remove();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void emit(String signal, Object... args) {
        try {
            String signalSign = signal.replaceAll("\\(|\\)|,| ", "");
            List<ReceiverSlot> receiverSlotList = signalSlot.get(signalSign);
            if (null == receiverSlotList) return;

            for (ReceiverSlot receiverSlot : receiverSlotList) {
                receiverSlot.slot.invoke(receiverSlot.receiver, args);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
