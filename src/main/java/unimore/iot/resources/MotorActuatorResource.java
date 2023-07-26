package unimore.iot.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import unimore.iot.model.MotorActuator;
import unimore.iot.request.IotRequest;
import unimore.iot.request.PlanRequest;
import unimore.iot.utilities.Deb;
import unimore.iot.request.IotResponse;
import unimore.iot.utilities.PlanHistory;

public class MotorActuatorResource extends CoapResource {
    private static final String OBJECT_TITLE = "MotorActuator";
    private final Gson gson;

    private final MotorActuator motorActuator;
    private final PlanHistory planHistory;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Constructor + init
    public MotorActuatorResource(String name, MotorActuator motorActuator)
    {
        super(name);
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new GsonBuilder().disableHtmlEscaping().create();

        this.motorActuator = motorActuator;
        this.planHistory = new PlanHistory();   //  empty history related to the current MotorActuator

        //  Start observation relationship
        setObservable(true);
        setObserveType(CoAP.Type.CON);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  GET --> motor status (which includes his sensors)
    @Override
    public void handleGET(CoapExchange exchange) {
        try
        {
            String responseBody = this.gson.toJson(this.motorActuator);

            //  TODO    SenMLPack
            //  1 SenMLRecord per ogni variabile del motor actuator (rmp -> "1/min")

            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    //  POST (Body empty) --> start default program (RISCIACQUO)
    @Override
    public void handlePOST(CoapExchange exchange)
    {
        try
        {
            //  Motor already running --> No need to start it again
            if (this.motorActuator.getRunning()) {
                String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CONFLICT.value, "Motor already running -> No need to start it again"));
                exchange.respond(CoAP.ResponseCode.CONFLICT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            }
            else
            {
                this.motorActuator.startMotor();  //  turn on the motor
                changed();
                String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Motor ON"));
                exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            }
        }
        catch (Exception e)
        {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void handlePUT(CoapExchange exchange) {

        //  (Double check: already checked) check if the motor is running before setting a program
        if (!this.motorActuator.getRunning()) {
            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.PRECONDITION_FAILED.value,
                    "Motor still OFF -> It needs to be started with a POST before setting a program"));
            exchange.respond(CoAP.ResponseCode.PRECONDITION_FAILED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            return;
        }

        try
        {
            String receivedPayload = new String(exchange.getRequestPayload());  //  Extract payload
            IotRequest iotRequest = this.gson.fromJson(receivedPayload, IotRequest.class);    //  deserialize

            if (iotRequest != null) {
                String command = iotRequest.getName();  //  actual String command
                String responseBody = null;

                switch (command) {

                    //  STOP request
                    case PlanRequest.STOP -> {
                        this.motorActuator.interrupt();

                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CONTENT.value, "Motor OFF"));
                        exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        changed();
                    }

                    case PlanRequest.LANA -> {
                        this.planHistory.increaseLana(this.motorActuator);  //  update history
                        this.motorActuator.startPlan(PlanRequest.LANA);  //  start plan
                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + command));
                        exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        changed();
                    }
                    case PlanRequest.RISCIACQUO -> {
                        this.planHistory.increaseRisciacquo(this.motorActuator);  //  update history
                        this.motorActuator.startPlan(PlanRequest.RISCIACQUO);  //  start plan
                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + command));
                        exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        changed();
                    }
                    case PlanRequest.SINTETICI -> {
                        this.planHistory.increaseSintetici(this.motorActuator);  //  update history
                        this.motorActuator.startPlan(PlanRequest.SINTETICI);  //  start plan
                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + command));
                        exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        changed();
                    }
                    case PlanRequest.COTONE -> {
                        this.planHistory.increaseCotone(this.motorActuator);  //  update history
                        this.motorActuator.startPlan(PlanRequest.COTONE);  //  start plan
                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + command));
                        exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        changed();
                    }
                    case PlanRequest.DELICATI -> {
                        this.planHistory.increaseDelicati(this.motorActuator);  //  update history
                        this.motorActuator.startPlan(PlanRequest.DELICATI);  //  start plan
                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + command));
                        exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        changed();
                    }
                    default -> {
                        responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.BAD_REQUEST.value, "Error: switch program selector (MotorActuatorResource-handlePUT"));
                        exchange.respond(CoAP.ResponseCode.BAD_REQUEST, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                    }
                }
            }
            //  iotRequest = null
            else {
                String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.BAD_REQUEST.value, "Empty PUT request"));
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            }
        }
        catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


}
