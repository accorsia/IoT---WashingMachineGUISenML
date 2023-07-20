package unimore.iot.client;
import javafx.scene.control.TextArea;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;

public class CoapObserveClient {

    private CoapObserveRelation relation = null;
    private String target;

    public String run(String passedResource, int serverPort, TextArea textArea) {
        System.out.println("\n--- Starting to observe '" + passedResource + "' ---\n");
        this.target = passedResource;
        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        String targetCoapResourceURL = COAP_ENDPOINT + passedResource;   //  resource target uri
        CoapClient client = new CoapClient(targetCoapResourceURL);  //  unimore.iot.client

        //  Request
        Request request = Request.newGet().setURI(targetCoapResourceURL).setObserve();
        request.setConfirmable(true);

        //  Observation action
        StringBuilder prettyPrintBuilder = new StringBuilder();
        relation = client.observe(request, new CoapHandler() {
            @Override
            public void onLoad(CoapResponse coapResponse) {
                String prettyPrint = Utils.prettyPrint(coapResponse);

                System.out.println("[OBSERVATION]\n");
                System.out.println(prettyPrint);

                if (textArea != null)
                    textArea.setText(prettyPrint.replace("Response", "Observation"));

                prettyPrintBuilder.append(prettyPrint);
            }

            @Override
            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        return prettyPrintBuilder.toString(); // Return the accumulated pretty print as a string
    }

    public void stopObservation() {
        if (relation != null) {
            System.out.println("--- end of Observation: " + target + "---");
            relation.proactiveCancel();
        }
        else
            System.out.println("--- No relation with " + target + " to stop ---");
    }
}
