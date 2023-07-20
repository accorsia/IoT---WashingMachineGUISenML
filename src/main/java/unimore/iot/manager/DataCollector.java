package unimore.iot.manager;

import javafx.scene.control.TextArea;
import unimore.iot.client.CoapObserveClientProcess;

public class DataCollector {
    public static void run(int serverPort, TextArea textArea) {
        CoapObserveClientProcess.run("motor", serverPort, textArea);
        CoapObserveClientProcess.run("door", serverPort, textArea);
        CoapObserveClientProcess.run("temperature", serverPort, textArea);
        CoapObserveClientProcess.run("weight", serverPort, textArea);
    }
}
