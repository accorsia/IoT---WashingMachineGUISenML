package unimore.iot.model;

import java.util.Random;

public class TemperatureSensor {
    private long timestamp;
    private double temp;
    private final String unit = "C";
    int idTemperature;

    private static final int TEMPERATURE_VALUE_BOND = 60;
    private static final int TEMPERATURE_START_VALUE = 40;

    private final transient Random random;    //  will contain random values (simulates measured values)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public TemperatureSensor(int idTemperature) {
        this.random = new Random();
        this.timestamp = System.currentTimeMillis();

        this.idTemperature = idTemperature;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Update temperature temp
    public double measureTemperatureValue() {
        this.temp = TEMPERATURE_START_VALUE + this.random.nextInt(TEMPERATURE_VALUE_BOND);
        this.timestamp = System.currentTimeMillis();

        return temp;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public double getTemp() {
        return temp;
    }

}
