package unimore.iot.client;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;
import unimore.iot.request.IotRequest;
import unimore.iot.request.PlanRequest;

import java.io.IOException;


public class CoapStopClient {
    public static String run(int serverPort) {
        String passedResource = "motor";    //  forced -> no need for parameters

        System.out.println("\n--- [PUT] Stopping: '" + passedResource + "' ---\n");

        //  Set Endpoint with right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT + passedResource);  //  client

        //  Request
        Request request = new Request(CoAP.Code.PUT);

        //  Set payload
        Gson gson = new Gson();
        String requestPayload = gson.toJson(new IotRequest(PlanRequest.STOP));
        request.setPayload(requestPayload);
        request.setConfirmable(true);

        try
        {
            //  Response
            CoapResponse coapResp = coapClient.advanced(request);

            String prettyPrint = "==[ PUT (stop) ]====================================================\n" + Utils.prettyPrint(coapResp);
            System.out.println(prettyPrint);
            return prettyPrint;
        }
        catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
