package unimore.iot.model;

import java.util.Random;

public class WeightSensor {
    private long timestamp;
    private double weight;
    private final String unit = "Kg";
    private boolean presence;
    int idWeight;


    private static final int WEIGHT_VALUE_BOND = 10;
    private static final int WEIGHT_START_VALUE = 1;

    private final transient Random random;    //  will contain random values (simulates measured values)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public WeightSensor(int idWeight)
    {
        this.random = new Random();
        this.timestamp = System.currentTimeMillis();
        this.presence = false;

        this.idWeight = idWeight;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double measureWeightValue() {
        this.weight = WEIGHT_START_VALUE + this.random.nextInt(WEIGHT_VALUE_BOND);
        this.timestamp = System.currentTimeMillis();
        this.presence = true;

        return weight;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean getPresence() {
        return presence;
    }

    public double getWeight() {
        return weight;
    }

}
