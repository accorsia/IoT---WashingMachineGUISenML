package unimore.iot.utilities;

import unimore.iot.model.MotorActuator;
import unimore.iot.request.PlanRequest;
import com.google.gson.Gson;


import java.util.LinkedList;

public class PlanHistory {

    private static Gson gson;

    private static int delicatiCount;
    private static int sinteticiCount;
    private static int cotoneCount;
    private static int lanaCount;
    private static int risciacquoCount;

    private static int totalCount;

    public static LinkedList<String> history = new LinkedList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public PlanHistory() {
        gson = new Gson();
    }

    public static LinkedList<String> getHistory() {
        return history;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void increaseDelicati(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.DELICATI, currentMotor.toString());
        history.add(gson.toJson(currentWash));

        delicatiCount++;
        totalCount++;
    }

    public void increaseSintetici(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.SINTETICI, currentMotor.toString());
        history.add(this.gson.toJson(currentWash));

        sinteticiCount++;
        totalCount++;
    }

    public void increaseCotone(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.COTONE, currentMotor.toString());
        history.add(gson.toJson(currentWash));

        cotoneCount++;
        totalCount++;
    }

    public void increaseLana(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.LANA, currentMotor.toString());
        history.add(gson.toJson(currentWash));

        lanaCount++;
        totalCount++;
    }

    public void increaseRisciacquo(MotorActuator currentMotor) {
        SingleWash currentWash = new SingleWash(currentMotor.getId(), System.currentTimeMillis(), PlanRequest.RISCIACQUO, currentMotor.toString());
        history.add(gson.toJson(currentWash));

        risciacquoCount++;
        totalCount++;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String myString() {
        StringBuilder sb = new StringBuilder();

        //  first part
        sb.append("PlanHistory{" + "delicatiCount=")
                .append(delicatiCount)
                .append(", sinteticiCount=")
                .append(sinteticiCount)
                .append(", cotoneCount=")
                .append(cotoneCount)
                .append(", lanaCount=")
                .append(lanaCount)
                .append(", risciacquoCount=")
                .append(risciacquoCount)
                .append(", totalCount=")
                .append(totalCount).append("}\n");

        sb.append("\n*** Plan log ***\n");

        for(String singleHistoryItem: history)
            sb.append(singleHistoryItem)
                    .append("\n");

        return sb.toString();
    }
}
