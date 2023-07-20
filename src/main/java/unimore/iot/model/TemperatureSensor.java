package unimore.iot.model;

import java.util.Random;

public class TemperatureSensor {
    private long timestamp;
    private double temperature;
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

    //  Update temperature temperature
    public double measureTemperatureValue() {
        this.temperature = TEMPERATURE_START_VALUE + this.random.nextInt(TEMPERATURE_VALUE_BOND);
        this.timestamp = System.currentTimeMillis();

        return temperature;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public double getTemperature() {
        return temperature;
    }

}
