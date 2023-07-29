package unimore.iot.serialization;

import unimore.iot.model.MotorActuator;
import unimore.iot.model.TemperatureSensor;
import unimore.iot.model.WeightSensor;
import unimore.iot.model.DoorActuator;

import unimore.iot.utilities.SingleWash;

public class SenMLSerialization {

    public static SenMLPack SingleWash2MLPack(SingleWash singleWash) {
        SenMLPack resPack = new SenMLPack();
        SenMLRecord senMLRecord = new SenMLRecord();

        senMLRecord.setBn("single_wash");
        senMLRecord.setT(singleWash.getTimestamp());
        senMLRecord.setN("motor_actuator_state");
        senMLRecord.setVs(singleWash.getMotorActuatorState());

        resPack.add(senMLRecord);
        return resPack;
    }

    public static SenMLPack MotorActuator2MLPack(MotorActuator motorActuator) {

//        int rpm;
//        boolean running;
//        int time;
//        double water;
//        double soap;
//        String currentProgram;
//        int idMotor;


//        SenMLRecord senMLRecord = new SenMLRecord();
//        senMLRecord.setBn(this.deviceId);
//        senMLRecord.setN(this.getName());
//        senMLRecord.setBver(SENSOR_VERSION);
//        senMLRecord.setU("m");
//        senMLRecord.setV(this.temperatureSensorDescriptor.getValue());
//        senMLRecord.setT(this.temperatureSensorDescriptor.getTimestamp());

        SenMLPack resPack = new SenMLPack();
        long timestamp = motorActuator.getTimestamp();
        String bn = "MotorActuator";

        //  [int] rpm
        SenMLRecord rpmRecord = new SenMLRecord();
        String unit = "1\\min";

        rpmRecord.setBn(bn);
        rpmRecord.setN("rpm");
        rpmRecord.setU(unit);
        rpmRecord.setV(motorActuator.getRpm());
        rpmRecord.setT(timestamp);
        resPack.add(rpmRecord);

        //  [boolean] running;
        SenMLRecord runningRecord = new SenMLRecord();
        runningRecord.setBn(bn);
        runningRecord.setN("running");
        runningRecord.setVb(motorActuator.getRunning());
        runningRecord.setT(timestamp);
        resPack.add(runningRecord);

        //  Exclude [int] time: already in 'long timestamp'
        /*SenMLRecord timeRecord = new SenMLRecord();
        timeRecord.setBn(bn);
        timeRecord.setN("time");
        timeRecord.setU("s");
        timeRecord.setV(motorActuator.getTime());
        timeRecord.setT(timestamp);
        resPack.add(timeRecord);*/

        //  [double] water
        SenMLRecord waterRecord = new SenMLRecord();
        unit = "l";

        waterRecord.setBn(bn);
        waterRecord.setN("water");
        waterRecord.setU(unit);
        waterRecord.setV(motorActuator.getWater());
        waterRecord.setT(timestamp);
        resPack.add(waterRecord);

        //  [double] soap
        SenMLRecord soapRecord = new SenMLRecord();
        unit = "l";

        soapRecord.setBn(bn);
        soapRecord.setN("soap");
        soapRecord.setU(unit);
        soapRecord.setV(motorActuator.getSoap());
        soapRecord.setT(timestamp);
        resPack.add(soapRecord);

        //  [String] currentProgram;
        SenMLRecord currentProgramRecord = new SenMLRecord();
        currentProgramRecord.setBn(bn);
        currentProgramRecord.setN("currentProgram");
        currentProgramRecord.setVs(motorActuator.getCurrentProgram());
        currentProgramRecord.setT(timestamp);
        resPack.add(currentProgramRecord);

        //  [int] idMotor
        SenMLRecord idMotorRecord = new SenMLRecord();
        idMotorRecord.setBn(bn);
        idMotorRecord.setN("idMotor");
        idMotorRecord.setV(motorActuator.getIdMotor());
        idMotorRecord.setT(timestamp);
        resPack.add(idMotorRecord);

        return resPack;
    }

    public static SenMLPack TemperatureSensor2MLPack(TemperatureSensor temperatureSensor) {
        SenMLPack resPack = new SenMLPack();
        long timestamp = temperatureSensor.getTimestamp();
        String bn = "TemperatureSensor";

        //  [double] temperature
        SenMLRecord temperatureRecord = new SenMLRecord();
        String unit = "Cel";

        temperatureRecord.setBn(bn);
        temperatureRecord.setN("temperature");
        temperatureRecord.setU(unit);
        temperatureRecord.setV(temperatureSensor.getTemperature());
        temperatureRecord.setT(timestamp);
        resPack.add(temperatureRecord);

        // [int] idTemperature
        SenMLRecord idTemperatureRecord = new SenMLRecord();
        idTemperatureRecord.setBn(bn);
        idTemperatureRecord.setN("idTemperature");
        idTemperatureRecord.setV(temperatureSensor.getIdTemperature());
        idTemperatureRecord.setT(timestamp);
        resPack.add(idTemperatureRecord);

        return resPack;
    }

    public static SenMLPack WeightSensor2MLPack(WeightSensor weightSensor) {
        SenMLPack resPack = new SenMLPack();
        long timestamp = weightSensor.getTimestamp();
        String bn = "WeightSensor";

        //  [double] weight
        SenMLRecord weightRecord = new SenMLRecord();
        String unit = "kg";

        weightRecord.setBn(bn);
        weightRecord.setN("weight");
        weightRecord.setU(unit);
        weightRecord.setV(weightSensor.getWeight());
        weightRecord.setT(timestamp);
        resPack.add(weightRecord);

        //  [boolean] presence
        SenMLRecord presenceRecord = new SenMLRecord();
        presenceRecord.setBn(bn);
        presenceRecord.setN("presence");
        presenceRecord.setVb(weightSensor.getPresence());
        presenceRecord.setT(timestamp);
        resPack.add(presenceRecord);

        //  [int] idWeight
        SenMLRecord idWeightRecord = new SenMLRecord();
        idWeightRecord.setBn(bn);
        idWeightRecord.setN("idWeight");
        idWeightRecord.setV(weightSensor.getIdWeight());
        idWeightRecord.setT(timestamp);
        resPack.add(idWeightRecord);

        return resPack;
    }

    public static SenMLPack DoorActuator2MLPack(DoorActuator doorActuator) {
        SenMLPack resPack = new SenMLPack();
        long timestamp = doorActuator.getTimestamp();
        String bn = "DoorActuator";

        //  [boolean] status
        SenMLRecord statusRecord = new SenMLRecord();
        statusRecord.setBn(bn);
        statusRecord.setN("status");
        statusRecord.setVb(doorActuator.getStatus());
        statusRecord.setT(timestamp);
        resPack.add(statusRecord);

        //  [int] idDoor
        SenMLRecord idDoorRecord = new SenMLRecord();
        idDoorRecord.setBn(bn);
        idDoorRecord.setN("idDoor");
        idDoorRecord.setV(doorActuator.getIdDoor());
        idDoorRecord.setT(timestamp);
        resPack.add(idDoorRecord);

        return resPack;
    }
}
