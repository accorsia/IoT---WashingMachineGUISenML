package unimore.iot.singleVersion;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unimore.iot.model.MotorActuator;
import unimore.iot.resources.*;


public class WashingMachineCoapServer extends CoapServer {
    private final static Logger logger = LoggerFactory.getLogger(WashingMachineCoapServer.class);
    public static int serverPort = 5683; //  shared with WashingMachineCoapClient.java

    //  add unimore.iot.resources
    public WashingMachineCoapServer(int port) {
        super(port);

        MotorActuator serverMotor = new MotorActuator(0);    //  starting Motor object
        
        //  Create-Add resources starting from the same Motor object.
        this.add(new MotorActuatorResource("motor", serverMotor));
        this.add(new TemperatureSensorResource("temperature", serverMotor.getTemperatureSensor()));
        this.add(new WeightSensorResource("weight", serverMotor.getWeightSensor()));
        this.add(new DoorActuatorResource("door", serverMotor));
        this.add(new PlanHistoryResource("history"));
    }

    public static void main(String[] args) {

        WashingMachineCoapServer coapServer = new WashingMachineCoapServer(serverPort);
        coapServer.start();

        coapServer.getRoot().getChildren().forEach(WashingMachineCoapServer::accept);
    }


    private static void accept(Resource resource) {
        System.out.printf("Resource %s -> URI: %s (Observable %b)%n", resource.getName(), resource.getURI(), resource.isObservable());
    }
}
