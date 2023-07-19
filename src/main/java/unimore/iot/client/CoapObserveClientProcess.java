package unimore.iot.client;

import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;

public class CoapObserveClientProcess {

    public static void run(String passedResource, int serverPort) {
        System.out.println("\n--- Starting to observe '" + passedResource + "' ---\n");

        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        String targetCoapResourceURL = COAP_ENDPOINT + passedResource;   //  resource target uri
        CoapClient client = new CoapClient(targetCoapResourceURL);  //  unimore.iot.client

        //  Request
        Request request = Request.newGet().setURI(targetCoapResourceURL).setObserve();
        request.setConfirmable(true);

        //  Observation action
        CoapObserveRelation relation = client.observe(request, new CoapHandler() {
            @Override
            public void onLoad(CoapResponse coapResponse) {
                System.out.println("[OBSERVATION]\n");
                //String content = coapResponse.getResponseText();
                System.out.printf("\nNotification Response Pretty Print: \n%s%n", Utils.prettyPrint(coapResponse));
            }

            @Override
            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        //  Observe the coap resource for 120 seconds then the observing relation is deleted
        try
        {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        relation.proactiveCancel();
    }
}
