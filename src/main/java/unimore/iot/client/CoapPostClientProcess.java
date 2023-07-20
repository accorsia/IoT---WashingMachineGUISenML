package unimore.iot.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class CoapPostClientProcess {

    public static String run(String passedResource, int serverPort) {
        System.out.println("\n--- [POST] Starting " + passedResource + " ---\n");

        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT + passedResource);  //  client

        //  Request
        Request request = new Request(CoAP.Code.POST);
        request.setConfirmable(true);

        try
        {
            //  Response
            CoapResponse coapResp = coapClient.advanced(request);

            String prettyPrint = "==[ POST ]====================================================\n" + Utils.prettyPrint(coapResp);
            System.out.println(prettyPrint);
            return prettyPrint;
        }
        catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
