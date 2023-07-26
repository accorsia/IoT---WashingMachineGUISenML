package unimore.iot.resources;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import unimore.iot.model.WeightSensor;
import unimore.iot.serialization.SenMLSerialization;

public class WeightSensorResource extends CoapResource {
    private static final String OBJECT_TITLE = "WeightSensor";
    private final Gson gson;

    private final WeightSensor weightSensor;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Constructor + initialization
    public WeightSensorResource(String name, WeightSensor weightSensor) {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        this.weightSensor = weightSensor;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            //  Already updated by MotorActuator.startPlan()
            String responseBody = this.gson.toJson(SenMLSerialization.WeightSensor2MLPack(this.weightSensor));
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_SENML_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }

    }

}