package unimore.iot.serialization;

import unimore.iot.utilities.SingleWash;

public class SenMLSerialization {

    public static SenMLRecord SW2ML(SingleWash singleWash) {
        // Creare un nuovo oggetto SenMLRecord
        SenMLRecord senMLRecord = new SenMLRecord();

        // Assegnare i valori appropriati dall'oggetto SingleWash all'oggetto SenMLRecord
        senMLRecord.setBn("single_wash"); // Sostituire "washing_machine" con il valore appropriato per il nome di base
        senMLRecord.setT(singleWash.getTimestamp());
        senMLRecord.setN("motor_actuator_state");
        senMLRecord.setVs(singleWash.getMotorActuatorState());


        return senMLRecord;
    }

}
