package unimore.iot.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import unimore.iot.WmGUI;
import unimore.iot.WmMultipleServer;
import unimore.iot.client.*;
import unimore.iot.request.PlanRequest;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static unimore.iot.utilities.Deb.*;

public class HelloController implements Initializable {

    @FXML

    //  default: -1 --> if during the form execution they're still -1 --> user input failed\invalid
    int serverChoice = -1;
    int nServerChoice = -1;

    //  Pane elements
    public Button launchProgramBtn;
    public Button createBtn;
    public Label createLbl;
    public Button getMotorBtn;
    public Button getTemperatureBtn;
    public Button shutMotorBtn;
    public Button getWeightBtn;
    public Button getDoorBtn;
    public Button getHistoryBtn;

    public TextField createFld;
    public TextField selectFld;

    public TextArea terminal;
    public TextArea terminalObs;    //  terminal for Observation pretty print

    public Button startMBtn;

    public ChoiceBox<String> planChoiceBox;
    public ChoiceBox<String> tgtChoiceBox;

    ////

    private CoapObserveClient coapObserveClient = new CoapObserveClient();


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void out(String message) {
        String sb = "\n**************************************\n" +
                message +
                "\n**************************************\n";
        terminal.appendText(sb);
    }
    public void ShowAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();    //  show alert and wait for exit
    }

    //  Load choiceBox items: Program plans (from 1 to 5)
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> planItems = new ArrayList<>();
        planItems.add(PlanRequest.COTONE);
        planItems.add(PlanRequest.LANA);
        planItems.add(PlanRequest.SINTETICI);
        planItems.add(PlanRequest.DELICATI);
        planItems.add(PlanRequest.RISCIACQUO);

        ArrayList<String> tgtItems = new ArrayList<>();
        tgtItems.add("motor");
        tgtItems.add("door");
        tgtItems.add("temperature");
        tgtItems.add("weight");

        planChoiceBox.getItems().addAll(planItems);
        tgtChoiceBox.getItems().addAll(tgtItems);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void createServers(ActionEvent event) {

        //  null ("") condition before --> avoid errors
        if(Objects.equals(createFld.getText(), "") || Integer.parseInt(createFld.getText()) <= 0) {
            ShowAlert("Error",
                    "wrong server number",
                    "write a number (> 0)");
            terminal.appendText(">>> ERROR: write a number (> 0)\n");
            terminal.setText(">>> Still NO server created ---> Don't press any button");
        }
        //  Server(s) already created --> can't create other (they would run over busy ports of old servers)
        else if (nServerChoice != -1) {
            ShowAlert("Error",
                    "server(s) already created",
                    "close app and relaunch it if you want to create new server(s)");
        } else {
            nServerChoice = Integer.parseInt(createFld.getText());
            WmGUI.StartServer(nServerChoice);
            terminal.appendText(retShowTitle("Starting " + nServerChoice + " server(s)"));
        }
    }

    public void selectServer(ActionEvent event) {
        if (selectFld.getText().equals("")) {
            ShowAlert("Error",
                    "empty SELECT field",
                    "select a server");
            serverChoice = -1;  //  reset in case it was previously selected
            terminal.appendText(">>> ERROR: Still NO server selected ---> Don't press any buttons <<<");
        }
        else if (Integer.parseInt(selectFld.getText()) < 0 || Integer.parseInt(selectFld.getText()) >= WmGUI.getnServer()) {
            ShowAlert("Error",
                    "Wrong server choice",
                    "select a number between [0, " + (WmGUI.getnServer() - 1) + "]");
            serverChoice = -1;  //  reset in case it was previously selected
            terminal.appendText(">>> ERROR: select a number between [0, " + (WmGUI.getnServer() - 1) + "] <<<\n");
            terminal.appendText(">>> Still NO server select ---> Don't press any buttons\n");
        }
        else {
            serverChoice = Integer.parseInt(selectFld.getText());
            terminal.appendText(retShowTitle("Server " + serverChoice + " selected"));
        }
    }

    //  BUTTONS   //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void startMotor(ActionEvent event) {
        if(nServerChoice == -1 || serverChoice == -1)
            ShowAlert("Error",
                    "Completa i 2 bottoni CREATE e SELECT",
                    "Completa la creazione dei server(s)");
        else {
            terminal.setText(CoapPostClient.run("motor", WmMultipleServer.getBasePort() + serverChoice));
            //DataCollector.run(WmMultipleServer.getBasePort() + serverChoice, null); //  null -> no output in form TextArea
        }
    }

    public void getMotor(ActionEvent event) {
        terminal.setText(CoapGetClient.run("motor", WmMultipleServer.getBasePort() + serverChoice));
    }

    public void getTemperature(ActionEvent event) {
        terminal.setText(CoapGetClient.run("temperature", WmMultipleServer.getBasePort() + serverChoice));
    }

    public void shutMotor(ActionEvent event) {
        terminal.setText(CoapStopClient.run(WmMultipleServer.getBasePort() + serverChoice));
    }

    public void getWeight(ActionEvent event) {
        terminal.setText(CoapGetClient.run("weight", WmMultipleServer.getBasePort() + serverChoice));
    }

    public void getDoor(ActionEvent event) {
        terminal.setText(CoapGetClient.run("door", WmMultipleServer.getBasePort() + serverChoice));
    }

    public void getHistory(ActionEvent event) {
        terminal.setText(CoapHistoryClient.run(WmMultipleServer.getBasePort() + serverChoice));
    }

    public void putPlan(ActionEvent event) throws InterruptedException {
        String planChoiceItem = planChoiceBox.getValue();
        terminal.setText("Running the program...it will take some time\n\n");
        terminal.appendText(CoapPutClient.run("motor", WmMultipleServer.getBasePort() + serverChoice, planChoiceItem));
    }

    public void openDoor(ActionEvent event) {
        terminal.setText(CoapPostClient.run("door", WmMultipleServer.getBasePort() + serverChoice));
    }

    public void startObservation(ActionEvent event) {
        String tgtRes = tgtChoiceBox.getValue();
        coapObserveClient.stopObservation();
        coapObserveClient.run(tgtRes, WmMultipleServer.getBasePort() + serverChoice, terminalObs);
    }

}