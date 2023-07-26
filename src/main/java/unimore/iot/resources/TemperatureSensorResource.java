package unimore.iot.resources;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.model.TemperatureSensor;
import unimore.iot.serialization.SenMLRecord;

public class TemperatureSensorResource extends CoapResource {

    private final static Logger logger = LoggerFactory.getLogger(TemperatureSensorResource.class);
    private static final String OBJECT_TITLE = "TemperatureSensor";
    private Gson gson;

    private TemperatureSensor temperatureSensor;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Constructor + initialization
    public TemperatureSensorResource(String name, TemperatureSensor temperatureSensor) {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        this.temperatureSensor = temperatureSensor;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            //  Already updated by MotorActuator.startPlan()

            //  Serialize
            String responseBody = this.gson.toJson(this.temperatureSensor);







            //String responseBody = this.gson.toJson(senMLRecord);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

}
