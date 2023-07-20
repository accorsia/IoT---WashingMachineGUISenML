package unimore.iot.client;

import javafx.scene.control.TextArea;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;
import unimore.iot.gui.HelloController;

public class CoapObserveClientProcess {

    public static String run(String passedResource, int serverPort, TextArea textArea) {
        System.out.println("\n--- Starting to observe '" + passedResource + "' ---\n");

        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        String targetCoapResourceURL = COAP_ENDPOINT + passedResource;   //  resource target uri
        CoapClient client = new CoapClient(targetCoapResourceURL);  //  unimore.iot.client

        //  Request
        Request request = Request.newGet().setURI(targetCoapResourceURL).setObserve();
        request.setConfirmable(true);

        //  Observation action
        StringBuilder prettyPrintBuilder = new StringBuilder();
        CoapObserveRelation relation = client.observe(request, new CoapHandler() {
            @Override
            public void onLoad(CoapResponse coapResponse) {
                String prettyPrint = Utils.prettyPrint(coapResponse);

                System.out.println("[OBSERVATION]\n");
                System.out.println(prettyPrint);

                textArea.setText(prettyPrint.replace("Response", "Observation"));
                prettyPrintBuilder.append(prettyPrint);
            }

            @Override
            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        /*//  Observe the coap resource for 120 seconds then the observing relation is deleted
        try {
            Thread.sleep(1200); // Sleep for 120 seconds (120,000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        relation.proactiveCancel();*/
        return prettyPrintBuilder.toString(); // Return the accumulated pretty print as a string
    }

    public String customPrettyPrint(String prettyPrint) {
        int indexTitle = prettyPrint.indexOf("\n");
        String newTitle = "==[ CoAP Observation ]============================================\n";

        return prettyPrint.replace("Response", "Observation");

    }
}
