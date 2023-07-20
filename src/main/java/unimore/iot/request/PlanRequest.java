package unimore.iot.request;

public class PlanRequest {

    /*
    Static variables as plan configuration
     */
    public static final String SINTETICI = "sintetici";
    public static final String COTONE = "cotone";
    public static final String DELICATI = "delicati";
    public static final String LANA = "lana";
    public static final String RISCIACQUO = "risciacquo";

    public static final String STOP = "stop";

    //  rpm
    public static final int HIGH_VELOCITY = 1400;
    public static final int MEDIUM_VELOCITY = 800;
    public static final int LOW_VELOCITY = 400;

    //  water: related to the load weight
    public static final double HIGH_WATER = 1.400;
    public static final double MEDIUM_WATER = 8.00;
    public static final double LOW_WATER = 4.00;

    //  soap
    public static final double HIGH_SOAP = 0.2;
    public static final double MEDIUM_SOAP = 0.15;
    public static final double LOW_SOAP = 0.1;

    //  temperature [40,100] ranges --> will change rpm values
    public static final double HIGHEST_TEMP = 100;
    public static final double MEDIUM_TEMP = 80;
    public static final double LOW_TEMP = 60;
    public static final double LOWEST_TEMP = 40;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String plan;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public PlanRequest(String plan) {
        this.plan = plan;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
