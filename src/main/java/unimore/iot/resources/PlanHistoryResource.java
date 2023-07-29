package unimore.iot.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.serialization.SenMLPack;
import unimore.iot.serialization.SenMLRecord;
import unimore.iot.utilities.PlanHistory;

import java.util.LinkedList;

public class PlanHistoryResource extends CoapResource {
    private final static Logger logger = LoggerFactory.getLogger(MotorActuatorResource.class);
    private static final String OBJECT_TITLE = "Plan History";
    private final Gson gson;

    private final LinkedList<SenMLPack> planHistory; //  list: all server machines history combined

    public PlanHistoryResource(String name) {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new GsonBuilder().disableHtmlEscaping().create();

        this.planHistory = PlanHistory.getHistory();

    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            //  JSON <- ArrayList<SenMLPack>
            String responseBody = this.gson.toJson(this.planHistory);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_SENML_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
