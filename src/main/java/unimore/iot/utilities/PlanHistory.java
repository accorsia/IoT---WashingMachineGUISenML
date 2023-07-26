package unimore.iot.utilities;

import com.google.gson.GsonBuilder;
import unimore.iot.model.MotorActuator;
import unimore.iot.request.PlanRequest;
import com.google.gson.Gson;
import unimore.iot.serialization.SenMLRecord;


import java.util.LinkedList;

import static unimore.iot.serialization.SenMLSerialization.SW2ML;

public class PlanHistory {

    private static Gson gson;

    private static int delicatiCount;
    private static int sinteticiCount;
    private static int cotoneCount;
    private static int lanaCount;
    private static int risciacquoCount;

    private static int totalCount;

    public static LinkedList<SenMLRecord> history = new LinkedList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public PlanHistory() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static LinkedList<SenMLRecord> getHistory() {
        return history;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void increaseDelicati(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.DELICATI, currentMotor.toString());
        history.add(SW2ML(currentWash));

        delicatiCount++;
        totalCount++;
    }

    public void increaseSintetici(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.SINTETICI, currentMotor.toString());
        history.add(SW2ML(currentWash));

        sinteticiCount++;
        totalCount++;
    }

    public void increaseCotone(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.COTONE, currentMotor.toString());
        history.add(SW2ML(currentWash));

        cotoneCount++;
        totalCount++;
    }

    public void increaseLana(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.LANA, currentMotor.toString());
        history.add(SW2ML(currentWash));

        lanaCount++;
        totalCount++;
    }

    public void increaseRisciacquo(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.RISCIACQUO, currentMotor.toString());
        history.add(SW2ML(currentWash));

        risciacquoCount++;
        totalCount++;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getPlanCounter() {
        StringBuilder sb = new StringBuilder();

        //  first part
        sb.append("PlanHistory:\n")
                .append("\n - delicatiCount=")
                .append(delicatiCount)
                .append("\n - sinteticiCount=")
                .append(sinteticiCount)
                .append("\n - cotoneCount=")
                .append(cotoneCount)
                .append("\n - lanaCount=")
                .append(lanaCount)
                .append("\n - risciacquoCount=")
                .append(risciacquoCount)
                .append("\n - totalCount=")
                .append(totalCount).append("}\n");

        sb.append("\n");

        return sb.toString();
    }
}
