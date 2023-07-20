package unimore.iot.manager;

import javafx.scene.control.TextArea;
import unimore.iot.client.CoapObserveClientProcess;

public class DataCollector {
    public static void run(int serverPort, TextArea textArea) {
        CoapObserveClientProcess coapObserveClient = new CoapObserveClientProcess();


        coapObserveClient.run("motor", serverPort, textArea);
        coapObserveClient.run("door", serverPort, textArea);
        coapObserveClient.run("temperature", serverPort, textArea);
        coapObserveClient.run("weight", serverPort, textArea);
    }
}
