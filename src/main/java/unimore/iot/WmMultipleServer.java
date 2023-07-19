package unimore.iot;

import org.eclipse.californium.core.CoapServer;
import unimore.iot.model.MotorActuator;
import unimore.iot.resources.*;

import java.util.ArrayList;
import java.util.List;


public class WmMultipleServer extends CoapServer {

    static int basePort = 5684;
    static int nServer = 3;
    static List<WmMultipleServer> serverList = new ArrayList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  server constructor
    public WmMultipleServer(int listId, int port) {
        super(port);

        MotorActuator serverMotor = new MotorActuator(listId);    //  starting Motor object

        //  Create-Add resources starting from the same Motor object.
        this.add(new MotorActuatorResource("motor", serverMotor));
        this.add(new TemperatureSensorResource("temperature", serverMotor.getTemperatureSensor()));
        this.add(new WeightSensorResource("weight", serverMotor.getWeightSensor()));
        this.add(new DoorActuatorResource("door", serverMotor));
        this.add(new PlanHistoryResource("history"));
    }

    public static int getBasePort() {
        return basePort;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getnServer() {
        return nServer;
    }

    public static void main(String[] args) {
        create(nServer);
    }

    public static void create(int nServer) {
        WmMultipleServer.nServer = nServer;
        System.setProperty("DEBUG", "coap");

        System.out.println(">>> Creating " + nServer + " servers...");

        //  Server id = list index
        for (int i = 0; i < nServer; i++) {
            System.out.println("\n>>> Creating MultipleServer[" + i + "], port: " + basePort + i + "\n");
            WmMultipleServer coapServer = new WmMultipleServer(i, basePort + i);
            serverList.add(coapServer);

            System.out.println("\n>>> Starting WashingMachineMultipleServer[" + i + "]...\n");
            coapServer.start();

            coapServer.getRoot().getChildren().forEach(resource ->
            {
                System.out.printf("Resource %s -> URI: %s (Observable %b)%n", resource.getName(), resource.getURI(),
                        resource.isObservable());
            });
        }

    }
}
