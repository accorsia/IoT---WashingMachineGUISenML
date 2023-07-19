package unimore.iot.resources;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.utilities.PlanHistory;
import unimore.iot.utilities.SingleWash;

import java.util.LinkedList;

public class PlanHistoryResource extends CoapResource {
    private final static Logger logger = LoggerFactory.getLogger(MotorActuatorResource.class);
    private static final String OBJECT_TITLE = "Plan History";
    private Gson gson;

    private LinkedList<String> planHistory; //  list: all server machines history combined


    public PlanHistoryResource(String name) {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        this.planHistory = PlanHistory.getHistory();
    }

    //  GET --> String of the global history
    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            //  return text representation of the global history
            String responseBody = PlanHistory.myString();

            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
