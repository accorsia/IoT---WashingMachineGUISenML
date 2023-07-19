package unimore.iot.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class CoapHistoryClientProcess {
    public static String run(int serverPort) {
        String passedResource = "history";
        System.out.println("\n--- [GET] '" + passedResource + "' status ---\n");

        // Set Endpoint with the right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT + passedResource);

        // Request
        Request request = new Request(CoAP.Code.GET);
        request.setConfirmable(true);

        CoapResponse coapResp = null;

        // Response
        try
        {
            coapResp = coapClient.advanced(request);
            if (coapResp != null && coapResp.isSuccess()) {
                //String responseBody = coapResp.getResponseText();
                System.out.printf("[GET] Response Pretty Print: \n%s%n", Utils.prettyPrint(coapResp));

            } else {
                System.out.println("[GET] Error: Failed to get a valid response.");
            }
        } catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }
        
        return Utils.prettyPrint(coapResp);
    }

}
