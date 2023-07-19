package unimore.iot.singleVersion;

import unimore.iot.client.*;
import unimore.iot.request.PlanRequest;

public class WashingMachineCoapClient {


    public static void main(String[] args) {

        int serverPort = WashingMachineCoapServer.serverPort;
        
        CoapObserveClientProcess.run("motor", serverPort);

        CoapPostClientProcess.run("motor", serverPort);

        CoapGetClientProcess.run("motor", serverPort);

        CoapGetClientProcess.run("weight", serverPort);

        CoapGetClientProcess.run("temperature", serverPort);

        CoapStopClientProcess.run(serverPort); //  engine OFF
        CoapPutClientProcess.run("motor", serverPort, PlanRequest.LANA);    //  handled error

        CoapGetClientProcess.run("motor", serverPort);

        CoapPostClientProcess.run("motor", serverPort); //  engine ON

        CoapGetClientProcess.run("motor", serverPort);

        CoapPutClientProcess.run("motor", serverPort, PlanRequest.DELICATI);

        CoapGetClientProcess.run("motor", serverPort);

        CoapPutClientProcess.run("motor", serverPort, PlanRequest.SINTETICI);

        CoapGetClientProcess.run("motor", serverPort);

        CoapHistoryClientProcess.run(serverPort);    //  history

        CoapStopClientProcess.run(serverPort);



    }

}
