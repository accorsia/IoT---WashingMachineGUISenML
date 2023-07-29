package unimore.iot.utilities;

import com.google.gson.GsonBuilder;
import unimore.iot.model.MotorActuator;
import unimore.iot.request.PlanRequest;
import com.google.gson.Gson;
import unimore.iot.serialization.SenMLPack;


import java.util.LinkedList;

import static unimore.iot.serialization.SenMLSerialization.SingleWash2MLPack;

public class PlanHistory {

    private static Gson gson;

    private static int delicatiCount;
    private static int sinteticiCount;
    private static int cotoneCount;
    private static int lanaCount;
    private static int risciacquoCount;

    private static int totalCount;

    public static LinkedList<SenMLPack> history = new LinkedList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public PlanHistory() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static LinkedList<SenMLPack> getHistory() {
        return history;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void increaseDelicati(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.DELICATI, currentMotor.toString());
        history.add(SingleWash2MLPack(currentWash));

        delicatiCount++;
        totalCount++;
    }

    public void increaseSintetici(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.SINTETICI, currentMotor.toString());
        history.add(SingleWash2MLPack(currentWash));

        sinteticiCount++;
        totalCount++;
    }

    public void increaseCotone(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.COTONE, currentMotor.toString());
        history.add(SingleWash2MLPack(currentWash));

        cotoneCount++;
        totalCount++;
    }

    public void increaseLana(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.LANA, currentMotor.toString());
        history.add(SingleWash2MLPack(currentWash));

        lanaCount++;
        totalCount++;
    }

    public void increaseRisciacquo(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.RISCIACQUO, currentMotor.toString());
        history.add(SingleWash2MLPack(currentWash));

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
