package unimore.iot.client;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;
import unimore.iot.request.PlanRequest;

import java.io.IOException;

public class CoapPutClientProcess {

    public static String run(String passedResource, int serverPort, String passedProgram) {
        System.out.println("\n--- [PUT] Selecting program: '" + passedProgram + "' ---\n");

        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT + passedResource);  //  client

        //  Request
        Request request = new Request(CoAP.Code.PUT);

        //  Set payload
        Gson gson = new Gson();
        String requestPayload = gson.toJson(new PlanRequest(passedProgram));   //  --- eg: COTONE ---
        request.setPayload(requestPayload);
        request.setConfirmable(true);

        System.out.printf("[PUT] Request Pretty Print: \n%s%n", Utils.prettyPrint(request));  //    debug request

        //  send Request --> get Response
        CoapResponse coapResp = null;

        try
        {
            coapResp = coapClient.advanced(request);
            System.out.printf("[PUT] Response Pretty Print: \n%s%n", Utils.prettyPrint(coapResp));   // debug response
        } catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }

        return Utils.prettyPrint(coapResp);
    }
}
