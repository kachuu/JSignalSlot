# SignalSlot  
  Implement a signal & slot mechanism similar to Qt in Java.  
  
### How Signals and Slots Work  
The signal corresponds to multiple slots, with each slot being a reference to a receiver. When the signal is emitted, it will invoke each slot associated with this signal, and the invocation order of the slots depends on their position in the slot list. The later a slot is added, the later it will be called.  
![image](https://github.com/kachuu/SignalSlot/blob/main/SignalSlot1.jpg)  
  
### You can connect any number of signals to a single slot, and signals can be connected to any number of slots  
![image](https://github.com/kachuu/SignalSlot/blob/main/abstract-connections.png)  
  
### Redirecting a signal to another slot  
I've considered a mechanism similar to Qt's where one signal directly connects to another signal (whenever one signal is emitted, it immediately emits a second signal). However, I feel that such a mechanism isn't extremely important. It simply saves writing an extra function (possibly saving around 3 lines of code), and it's not very friendly for debugging since it's inconvenient to set breakpoints or output debug logs.  
![image](https://github.com/kachuu/SignalSlot/blob/main/SignalSlot.jpg)  
  
## Example  
### Connect signals to slots  
Create a sender object that inherits from *QObject*.  
```bash  
public class Sender extends QObject {
    //to do something
}
```  
  
Create a receiver object that inherits from *QObject* and has a slot function that takes an integer parameter.  
```bash  
public class Receiver extends QObject {

    public void slot_1param(Integer v) {
        //to do something
    }
}
```  
  
Use the *connect* function to connect signal with slot.  
*signal_1param(Integer)* is used to mark signal values; it is not a real function itself.  
*slot_1param(Integer)* is the function signature of the slot function, which must match the actual function.  
```bash  
connect(Sender, "signal_1param(Integer)", Receiver, "slot_1param(Integer)");
```  
  
### Send a signal  
Call the *emit* function, passing the signal identifier and the required parameter (using the integer 1000 here), thereby triggering signal processing.  
```bash  
emit("signal_1param(Integer)", 1000);
```  
  
### Disconnect signals to slots  
Use the *disconnect* function to break the connection between signals and slots, similar to the method used in the *connect* function.  
When the third parameter is *not null*, only the connections between the signal and slots related to *Receiver* object are removed. Connections between other object signals and slots remain unaffected.  
```bash  
disconnect(Sender, "signal_1param(Integer)", Receiver, "slot_1param(Integer)");
```  
  
When the third parameter is *null*, remove all connections of signals and slots associated with *Sender*.  
```bash  
disconnect(Sender, "signal_1param(Integer)", null, "");
```  
  