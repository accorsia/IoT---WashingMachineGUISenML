package unimore.iot.model;

import com.google.gson.annotations.Expose;
import unimore.iot.resources.TemperatureSensorResource;

import java.util.Random;



public class TemperatureSensor {
    private static final int TEMPERATURE_VALUE_BOND = 60;
    private static final int TEMPERATURE_START_VALUE = 40;
    private final transient Random random;    //  will contain random values (simulates measured values)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Expose
    private long timestamp;
    @Expose
    private double temp;
    @Expose
    private final String unit = "C";
    @Expose
    int idTemperature;

    private TemperatureSensorResource listener; //  resource listening for change of sensor statsu

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
        notifyListener();
        return temp;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setListener(TemperatureSensorResource listener) {
        this.listener = listener;
    }

    private void notifyListener() {
        if (listener != null)
            listener.onTemperatureChanged(this.temp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double getTemp() {
        return temp;
    }

}
