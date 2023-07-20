package unimore.iot.client;
import javafx.scene.control.TextArea;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;

public class CoapObserveClient {

    private CoapObserveRelation relation = null;
    private String target;      //  target resource name
    private String prettyPrint; //  target var

    public String run(String passedResource, int serverPort, TextArea textArea) {
        System.out.println("\n--- Starting to observe '" + passedResource + "' ---\n");
        this.target = passedResource;

        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        //  Target resource
        String targetCoapResourceURL = COAP_ENDPOINT + passedResource;   //  uri
        CoapClient client = new CoapClient(targetCoapResourceURL);

        //  Request
        Request request = Request.newGet().setURI(targetCoapResourceURL).setObserve();
        request.setConfirmable(true);

        //  Observation relation
        relation = client.observe(request, new CoapHandler() {
            @Override
            public void onLoad(CoapResponse coapResponse) {
                prettyPrint = Utils.prettyPrint(coapResponse);

                System.out.println("[OBSERVATION]\n");
                System.out.println(prettyPrint);

                if (textArea != null)
                    textArea.setText(prettyPrint.replace("Response", "Observation"));
            }

            @Override
            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        return prettyPrint;
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
