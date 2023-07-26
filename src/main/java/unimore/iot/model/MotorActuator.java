package unimore.iot.model;

import unimore.iot.utilities.Deb;

import static unimore.iot.request.PlanRequest.*;

public class MotorActuator {

    int rpm;
    boolean running;
    int time;
    double water;
    double soap;
    String currentProgram;
    int idMotor;
    long timestamp;


    //  Included models
    WeightSensor weightSensor;
    TemperatureSensor temperatureSensor;
    DoorActuator doorActuator;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public MotorActuator(int idMotor) {
        this.rpm = 0;
        this.running = false;
        this.currentProgram = null;

        //  Create object with the same 'id'
        this.weightSensor = new WeightSensor(idMotor);
        this.temperatureSensor = new TemperatureSensor(idMotor);
        this.doorActuator = new DoorActuator(idMotor);
        this.idMotor = idMotor;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Pass the sensor model for the sensor resources
    public WeightSensor getWeightSensor() {
        return this.weightSensor;
    }

    public TemperatureSensor getTemperatureSensor() {
        return this.temperatureSensor;
    }

    public DoorActuator getDoorActuator() {
        return doorActuator;
    }

    public boolean getRunning() {
        return running;
    }

    public int getId() {
        return this.idMotor;
    }

    public int getRpm() {
        return this.rpm;
    }

    public int getTime() {
        return time;
    }

    public double getWater() {
        return water;
    }

    public double getSoap() {
        return soap;
    }

    public String getCurrentProgram() {
        return currentProgram;
    }

    public int getIdMotor() {
        return idMotor;
    }

    public long getTimestamp() {
        return timestamp;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Start motor off with default: RISCIACQUO
    public void startMotor() {
        setValue(RISCIACQUO);
        this.running = true;    //  turn ON the machine
        this.timestamp = System.currentTimeMillis();

        try
        {
            //  Close door
            doorActuator.setStatus("close");
            System.out.println(Deb.debHelp() + "Door closed!");

            System.out.print(Deb.debHelp() + "Motor starting with plan: " + currentProgram + "...");
            Thread.sleep(time); //  Running for 'time' [ms]
            System.out.println(" --> plan finished!");

            //  Open door
            doorActuator.setStatus("open");
            System.out.println(Deb.debHelp() + "Door opened!");
        }
        catch (InterruptedException e) {
            System.out.println(Deb.debHelp() + "Problem with 'Thread.sleep(time)' in startPlan()");
            e.printStackTrace();
        }
    }

    public void startPlan(String plan) {
        setValue(plan);     //  configure program values

        try
        {
            //  Close door
            doorActuator.setStatus("close");
            System.out.println(Deb.debHelp() + "Door closed!");

            System.out.print("Motor running with plan: " + currentProgram + "...");
            Thread.sleep(time); //  Running for 'time' [ms]
            System.out.println(" --> plan finished");

            //  Open door
            doorActuator.setStatus("open");
            System.out.println(Deb.debHelp() + "Door opened!");
        }
        catch (InterruptedException e)
        {
            System.out.println(Deb.debHelp() + "Problem with 'Thread.sleep(time)' in startPlan()");
            e.printStackTrace();
        }

    }

    public void setValue(String plan)
    {
        currentProgram = plan;
        //  update sensors
        double currentTemp = this.temperatureSensor.measureTemperatureValue();
        double currentWeight = this.weightSensor.measureWeightValue();

        setTimeAccording2Temp(currentTemp);

        switch (plan) {
            case COTONE -> {
                time *= 120;
                rpm = HIGH_VELOCITY;
                water = HIGH_WATER * currentWeight;
                soap = HIGH_SOAP * water;
            }
            case SINTETICI -> {
                time *= 80;
                rpm = MEDIUM_VELOCITY;
                water = MEDIUM_WATER * currentWeight;
                soap = MEDIUM_SOAP * water;
            }
            case DELICATI -> {
                time *= 50;
                rpm = LOW_VELOCITY;
                water = MEDIUM_WATER * currentWeight;
                soap = LOW_SOAP * water;
            }
            case LANA -> {
                time *= 40;
                rpm = LOW_VELOCITY;
                water = LOW_WATER * currentWeight;
                soap = LOW_SOAP * water;
            }
            case RISCIACQUO -> {
                time *= 20;
                rpm = LOW_VELOCITY;
                water = LOW_WATER * currentWeight;
                soap = 0;   //  risciacquo --> no soap
            }
            default -> {}

        }
    }

    /*
    According to the value of the temperature sensor, the 'time' configuration variable becomes a multiplier
    for the 'time' variable of the PLAN
     */
    public void setTimeAccording2Temp(double currentTemp)
    {
        //  CHECK:  if the temp sensor return a value that is too high --> wait until the machine cools off
        while (currentTemp+2 > HIGHEST_TEMP || currentTemp-2 < LOWEST_TEMP)
        {
            System.out.println(Deb.debHelp()
                    + "Il sensore di temperatura ha rilevato una temperatura (" + currentTemp + ") fuori range ["
                    + LOWEST_TEMP + ", " + HIGHEST_TEMP + "]" +" ---> Il macchinario andra in pausa per 3 secondi...");

            try { Thread.sleep(3000); } //  ms
            catch (InterruptedException e) { e.printStackTrace(); }

            //  Update temperature value
            currentTemp = this.temperatureSensor.measureTemperatureValue();
        }

        System.out.println(Deb.debHelp() + "Temperatura del macchinario accettabile!");

        //  If the sleep time is too long: gui crash as during sleep time even the gui is frozen
        if (currentTemp >= MEDIUM_TEMP)
            time = 60;
        else if (currentTemp <= MEDIUM_TEMP && currentTemp >= LOW_TEMP)
            time = 40;
        else
            time = 20;

    }

    public void interrupt()
    {
        this.running = false;   //  --> Needs 'startMotor()' again
        this.rpm = 0;
        this.time = 0;
        this.water = 0;
        this.soap = 0;
        this.currentProgram = null;

        System.out.println(Deb.debHelp() + "Stopping engine...Motor OFF");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Override --> excludes all sensors' information, just the essential value
    //  Not used! --> GET uses json serialization/deserialization
    @Override
    public String toString() {
        return "MotorActuator{" +
                "rpm=" + rpm +
                ", idMotor=" + idMotor +
                ", running=" + running +
                ", time=" + time +
                ", water=" + water +
                ", soap=" + soap +
                ", currentProgram='" + currentProgram + '\'' +
                ", weightSensor=" + weightSensor.getWeight() +
                ", temperatureSensor=" + temperatureSensor.getTemperature() +
                ", doorActuator=" + doorActuator.getStatus() +
                '}';
    }
}
