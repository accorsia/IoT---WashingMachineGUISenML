package unimore.iot.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.model.DoorActuator;
import unimore.iot.model.MotorActuator;
import unimore.iot.request.IotResponse;

import java.util.Objects;

public class DoorActuatorResource extends CoapResource {
    private final static Logger logger = LoggerFactory.getLogger(MotorActuatorResource.class);
    private static final String OBJECT_TITLE = "DoorActuator";
    private Gson gson;

    private MotorActuator motorActuator;
    private DoorActuator doorActuator;

    public DoorActuatorResource(String name, MotorActuator motorActuator) {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new GsonBuilder().disableHtmlEscaping().create();;

        this.motorActuator = motorActuator;
        this.doorActuator = this.motorActuator.getDoorActuator();   //  heir DoorActuator from the current MotorActuator
    }


    //  GET --> door status
    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            String responseBody = this.gson.toJson(this.doorActuator);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        try
        {
            boolean openOrClose;
            openOrClose = Objects.equals(exchange.getRequestText(), "\"true\"");    //  the response text includes: ""

            this.motorActuator.interrupt(); //  just in case it is running
            this.doorActuator.setStatus(openOrClose);   //  set door status according to payload

            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Door status updated"));
            exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void handlePOST(CoapExchange exchange) {
        try
        {
            //  Door = open --> already open
            if(this.doorActuator.getStatus()) {
                String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CONFLICT.value, "Door already open -> Motor not running"));
                exchange.respond(CoAP.ResponseCode.CONFLICT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            }
            //  Door = closed --> interrupt motor + open door
            else {
                this.motorActuator.interrupt();
                this.doorActuator.setStatus("close");

                String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Door closed -> Motor OFF"));
                exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            }

        } catch (Exception e) {
            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.BAD_REQUEST.value, "Error in DoorActuatorResource - handlePOST"));
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
    }
}
