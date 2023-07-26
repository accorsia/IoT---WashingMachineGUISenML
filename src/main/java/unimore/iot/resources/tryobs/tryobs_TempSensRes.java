package unimore.iot.resources.tryobs;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import unimore.iot.model.TemperatureSensor;
import unimore.iot.model.tryobs.tryobs_TempSens;

public class tryobs_TempSensRes extends CoapResource {

    private static final String OBJECT_TITLE = "TemperatureSensor";
    private final Gson gson;

    private final tryobs_TempSens temperatureSensor;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Constructor + initialization
    public tryobs_TempSensRes(String name, tryobs_TempSens temperatureSensor) {
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
