package unimore.iot.request;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

public class IotResponse {

    private static Gson gson = new Gson();
    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("message")
    private String message;

    public IotResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "IoTResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
