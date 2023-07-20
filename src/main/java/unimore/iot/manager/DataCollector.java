package unimore.iot.manager;

import javafx.scene.control.TextArea;
import unimore.iot.client.CoapObserveClient;

public class DataCollector {
    public static void run(int serverPort, TextArea textArea) {
        CoapObserveClient coapObserveClient = new CoapObserveClient();

        coapObserveClient.run("motor", serverPort, textArea);
        coapObserveClient.run("door", serverPort, textArea);
        coapObserveClient.run("temperature", serverPort, textArea);
        coapObserveClient.run("weight", serverPort, textArea);
    }
}
