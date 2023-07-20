package unimore.iot.request;

public class IotRequest {
    private String name;

    public IotRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
