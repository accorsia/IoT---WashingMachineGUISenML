package unimore.iot;

import unimore.iot.client.*;
import unimore.iot.request.PlanRequest;

import java.util.Scanner;

import static java.lang.System.exit;
import static unimore.iot.utilities.Deb.ShowTitle;
import static unimore.iot.utilities.Deb.ShowTitleBig;

public class WmGUI {
    static final String NO_PLAN = "noplan";
    static int multipleServerPort = WmMultipleServer.getBasePort();
    static int nServer;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getnServer() {
        return nServer;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void StartServer(int n) {
        WmGUI.nServer = n;

        ShowTitle("Starting server");
        WmMultipleServer.create(nServer);
    }

    public static int SelectServer() {
        ShowTitleBig("Select server");

        boolean wrongServer = true;
        int serverChoice = -1;

        while (wrongServer) {
            wrongServer = false;

            System.out.println(">> Premi -1 se vuoi uscire dal programma: ");
            System.out.print("> Quale server vuoi selezionare [da 0 a " + (nServer - 1) + "]: ");
            serverChoice = new Scanner(System.in).nextInt();

            if (serverChoice < -1 || serverChoice > (nServer - 1)) {
                System.out.println("\n>>> ERROR --> Inserisci un numero compreso tra [-1, " + (nServer - 1) + "] <<<\n");
                wrongServer = true;
            }
        }

        return serverChoice;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        //  Variable directors of user interaction
        int selectedVerb;
        String selectedPlan = NO_PLAN;
        int selectedServer;

        //  Start 'nServer' server
        StartServer(nServer);

        boolean bigLoop = true;

        //  Loop: select server
        while (bigLoop) {
            selectedServer = SelectServer();

            if (selectedServer == -1)
                bigLoop = false;
            else {
                boolean insideServerloop = true;

                //  Loop: what to do inside the server
                while (insideServerloop) {
                    System.out.print("\n###\tSelected server: [" + selectedServer + "]");

                    //  Select action
                    selectedVerb = getVerbChoice();
                    if (selectedVerb == 5) selectedPlan = getPlanChoice();

                    //  Switch --> Execute action
                    SwitchExecuteVerb(selectedVerb, selectedServer, selectedPlan);

                    //  Exit or continue
                    if (selectedVerb == 8)
                        insideServerloop = false;
                }
            }

        }

        ShowTitleBig("Closing program");
        exit(0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getVerbChoice() {
        ShowTitle("Menu");

        //  Return value
        int verbChoice = 0;
        int lastVerb = 8;   //  exit number parametrized

        boolean wrongVerbChoice = true;
        while (wrongVerbChoice) {
            wrongVerbChoice = false;

            System.out.println("1 - POST 'motor' (start)");
            System.out.println("2 - GET 'motor'");
            System.out.println("3 - GET 'temperature'");
            System.out.println("4 - GET 'weight'");
            System.out.println("5 - PUT 'motor': ");
            System.out.println("6 - PUT (stop) 'motor'");
            System.out.println("7 - GET global history");
            System.out.println("8 - Exit server");

            Scanner scanner = new Scanner(System.in);
            verbChoice = scanner.nextInt();

            if (verbChoice < 1 || verbChoice > lastVerb) {
                System.out.println("\n>>> ERROR --> Inserisci un numero compreso tra 1 e " + lastVerb + " <<<\n");
                wrongVerbChoice = true; //  repeat Menu
            }
        }

        return verbChoice;
    }

    public static String getPlanChoice() {
        ShowTitle("Plan choice");

        int planChoice = 0;

        boolean wrongPlanChoice = true;
        while (wrongPlanChoice) {
            wrongPlanChoice = false;

            System.out.println("1 - Cotoni");
            System.out.println("2 - Delicati");
            System.out.println("3 - Sintetici");
            System.out.println("4 - Lana");
            System.out.println("5 - Risciacquo");

            Scanner scanner = new Scanner(System.in);
            planChoice = scanner.nextInt();

            if (planChoice < 1 || planChoice > 5) {
                System.out.println("\n>>> ERROR: Inserisci un numero compreso tra 1 e 5 <<<\n");
                wrongPlanChoice = true; //  repeat Menu
            }
        }

        switch (planChoice) {
            case 1 -> {
                return PlanRequest.COTONE;
            }
            case 2 -> {
                return PlanRequest.DELICATI;
            }
            case 3 -> {
                return PlanRequest.SINTETICI;
            }
            case 4 -> {
                return PlanRequest.LANA;
            }
            case 5 -> {
                return PlanRequest.RISCIACQUO;
            }
        }

        return "error"; //  for some reason, the while check did not work
    }

    public static void SwitchExecuteVerb(int selectedVerb, int offServer, String eventualProgram) {
        switch (selectedVerb) {
            //  POST motor (start)
            case 1 -> CoapPostClientProcess.run("motor", multipleServerPort + offServer);
            //  GET motor
            case 2 -> CoapGetClientProcess.run("motor", multipleServerPort + offServer);
            //  GET temperature
            case 3 -> CoapGetClientProcess.run("temperature", multipleServerPort + offServer);
            //  GET weight
            case 4 -> CoapGetClientProcess.run("weight", multipleServerPort + offServer);
            //  PUT motor
            case 5 -> CoapPutClientProcess.run("motor", multipleServerPort + offServer, eventualProgram);
            //  PUT motor (stop)
            case 6 -> CoapStopClientProcess.run(multipleServerPort + offServer);
            //  GET global history
            case 7 -> CoapHistoryClientProcess.run(multipleServerPort);
        }
    }


}
