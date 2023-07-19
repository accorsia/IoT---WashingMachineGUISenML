package unimore.iot.singleVersion;

import unimore.iot.WmMultipleServer;
import unimore.iot.client.*;
import unimore.iot.request.PlanRequest;
import unimore.iot.utilities.Deb;

public class WashingMachineMultipleClient {
    static int multipleServerPort = WmMultipleServer.getBasePort();
    static int nServer = WmMultipleServer.getnServer();

    public static void main(String[] args) {

        for (int i=0; i<nServer; i++)
        {
            System.out.println("\n\n#############################################################");
            System.out.println("...Scrolling [" + i + "] server inside serverList...");
            System.out.println("#############################################################\n\n");

            CoapPostClientProcess.run("motor", multipleServerPort+i);
            CoapGetClientProcess.run("motor", multipleServerPort+i);
            CoapPutClientProcess.run("motor", multipleServerPort+i, PlanRequest.SINTETICI);
            CoapStopClientProcess.run(multipleServerPort+i);
            CoapPostClientProcess.run("motor", multipleServerPort+i);
            CoapPutClientProcess.run("motor", multipleServerPort+i, PlanRequest.LANA);
            CoapGetClientProcess.run("motor", multipleServerPort+i);

            Deb.ShowTitleBig("History");

            CoapHistoryClientProcess.run(multipleServerPort);   //  history is shared from all server --> no need to select ports
        }
    }


}
