package unimore.iot.model;

import unimore.iot.utilities.Deb;

import java.util.Objects;

public class DoorActuator {
    long timestamp; //  timestamp of last change
    boolean status;

    int idDoor;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public DoorActuator(int idDoor)
    {
        this.idDoor = idDoor;
        this.status = true;     //  start as 'open' --> it will close as soon as the machine starts
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public long getTimestamp() {
        return timestamp;
    }

    public boolean getStatus() {
        return status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setStatus(String status)
    {
        this.timestamp = System.currentTimeMillis();

        //  Check if the door is currently in the opposite state
        if (Objects.equals(status, "open") && !this.status) {
            this.status = true;
        }
        else if (Objects.equals(status, "close") && this.status)
            this.status = false;
        else
            System.out.println(Deb.debHelp() + "ERROR:\t DoorActuator received a wrong status value");
    }

    public void setStatus(boolean status)
    {
        this.timestamp = System.currentTimeMillis();

        //  Check if the door is currently in the opposite state
        if (status && !this.status)
            this.status = status;

    }

}
