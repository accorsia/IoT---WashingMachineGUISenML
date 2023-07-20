package unimore.iot.request;

import com.google.gson.annotations.SerializedName;
import org.eclipse.californium.core.coap.CoAP;

public class IotResponse {

    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("status_name")
    private String statusName;

    @SerializedName("message")
    private String message;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public IotResponse(int statusCode, String message) {
        this.statusName = CoAP.ResponseCode.valueOf(statusCode).name();    //  CoapResponse - name
        this.statusCode = statusCode;   //  CoapResponse - code
        this.message = message;
    }

}
