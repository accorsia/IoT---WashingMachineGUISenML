package unimore.iot.resources;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.model.TemperatureSensor;

public class TemperatureSensorResource extends CoapResource {

    private static final String OBJECT_TITLE = "TemperatureSensor";
    private final Gson gson;

    private final TemperatureSensor temperatureSensor;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Constructor + initialization
    public TemperatureSensorResource(String name, TemperatureSensor temperatureSensor) {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        this.temperatureSensor = temperatureSensor;
        this.temperatureSensor.setListener(this);

        //  Start observation relationship
        setObservable(true);
        setObserveType(CoAP.Type.CON);
    }

    // This method is called whenever the TemperatureSensor's state changes
    public void onTemperatureChanged(double temp) {
        changed();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            //  Serialize
            String responseBody = this.gson.toJson(this.temperatureSensor, TemperatureSensor.class);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


}
