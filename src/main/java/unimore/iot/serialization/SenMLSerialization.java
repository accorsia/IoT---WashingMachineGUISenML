package unimore.iot.serialization;

import unimore.iot.utilities.SingleWash;

public class SenMLSerialization {

    public static SenMLRecord SW2ML(SingleWash singleWash) {
        SenMLRecord senMLRecord = new SenMLRecord();

        //  Match right values
        senMLRecord.setBn("single_wash");
        senMLRecord.setT(singleWash.getTimestamp());
        senMLRecord.setN("motor_actuator_state");
        senMLRecord.setVs(singleWash.getMotorActuatorState());

        return senMLRecord;
    }

}
