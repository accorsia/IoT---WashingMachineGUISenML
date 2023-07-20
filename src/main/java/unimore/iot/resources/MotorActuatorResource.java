package unimore.iot.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.model.MotorActuator;
import unimore.iot.request.PlanRequest;
import unimore.iot.utilities.Deb;
import unimore.iot.request.IotResponse;
import unimore.iot.utilities.PlanHistory;

public class MotorActuatorResource extends CoapResource {
    private final static Logger logger = LoggerFactory.getLogger(MotorActuatorResource.class);
    private static final String OBJECT_TITLE = "MotorActuator";
    private Gson gson;

    private MotorActuator motorActuator;
    private PlanHistory planHistory;

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
        }

        try
        {
            String receivedPayload = new String(exchange.getRequestPayload());  //  Extract payload

            //  STOP condition
            if (receivedPayload.equals("stop"))
            {
                this.motorActuator.interrupt();

                System.out.println(Deb.debHelp() + "Stopping engine...Motor OFF");
                String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CONTENT.value, "Motor OFF"));
                exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
            }
            //  PUT program condition
            else
            {
                PlanRequest payloadRequest = this.gson.fromJson(receivedPayload, PlanRequest.class);    //  deserialize

                if (payloadRequest != null && payloadRequest.getPlan() != null && payloadRequest.getPlan().length() > 0) {

                    switch (payloadRequest.getPlan()) {
                        case PlanRequest.LANA -> {
                            this.planHistory.increaseLana(this.motorActuator);  //  update history
                            this.motorActuator.startPlan(PlanRequest.LANA);  //  start plan
                            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + payloadRequest.getPlan()));
                            exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                            changed();
                        }
                        case PlanRequest.RISCIACQUO -> {
                            this.planHistory.increaseRisciacquo(this.motorActuator);  //  update history
                            this.motorActuator.startPlan(PlanRequest.RISCIACQUO);  //  start plan
                            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + payloadRequest.getPlan()));
                            exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                            changed();
                        }
                        case PlanRequest.SINTETICI -> {
                            this.planHistory.increaseSintetici(this.motorActuator);  //  update history
                            this.motorActuator.startPlan(PlanRequest.SINTETICI);  //  start plan
                            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + payloadRequest.getPlan()));
                            exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                            changed();
                        }
                        case PlanRequest.COTONE -> {
                            this.planHistory.increaseCotone(this.motorActuator);  //  update history
                            this.motorActuator.startPlan(PlanRequest.COTONE);  //  start plan
                            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + payloadRequest.getPlan()));
                            exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                            changed();
                        }
                        case PlanRequest.DELICATI -> {
                            this.planHistory.increaseDelicati(this.motorActuator);  //  update history
                            this.motorActuator.startPlan(PlanRequest.DELICATI);  //  start plan
                            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.CHANGED.value, "Washing Machine running: " + payloadRequest.getPlan()));
                            exchange.respond(CoAP.ResponseCode.CHANGED, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                            changed();
                        }
                        default -> {
                            String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.BAD_REQUEST.value, "ERROR selecting the program in MotorActuatorResource - handlePUT"));
                            exchange.respond(CoAP.ResponseCode.BAD_REQUEST, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                        }

                    }
                }
                //  Empty payload --> no program selected
                else {
                    String responseBody = this.gson.toJson(new IotResponse(CoAP.ResponseCode.BAD_REQUEST.value, "ERROR in selected program"));
                    exchange.respond(CoAP.ResponseCode.BAD_REQUEST, responseBody, MediaTypeRegistry.APPLICATION_JSON);
                }
            }
        } catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }

    }
}
