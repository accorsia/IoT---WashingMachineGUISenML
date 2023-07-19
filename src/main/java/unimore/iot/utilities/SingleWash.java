package unimore.iot.utilities;
/**
 * The `SingleWash` class represents a single wash operation.
 * It contains information about the timestamp, motor actuator, and selected plan.
 */
public class SingleWash {
    public long timestamp;
    public String motorActuatorState;
    public String plan;
    public int id; //  id of the MotorActuator

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs a new instance of the `SingleWash` class with the specified timestamp, motor actuator, and plan.
     *
     * @param timestamp     The timestamp of the wash operation.
     * @param motorActuatorState The String representation of the current state of the motor actuator associated with the wash operation.
     * @param plan          The selected plan for the wash operation.
     */
    public SingleWash(int id, long timestamp, String plan, String motorActuatorState) {
        this.id = id;
        this.timestamp = timestamp;
        this.motorActuatorState = motorActuatorState;
        this.plan = plan;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMotorActuatorState() {
        return motorActuatorState;
    }

    public void setMotorActuatorState(String motorActuatorState) {
        this.motorActuatorState = motorActuatorState;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return "SingleWash{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", plan='" + plan + '\'' +
                ", motorActuatorState='" + motorActuatorState + '\'' +
                '}';
    }
}
