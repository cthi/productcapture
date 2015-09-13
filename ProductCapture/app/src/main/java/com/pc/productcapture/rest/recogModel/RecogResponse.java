package com.pc.productcapture.rest.recogModel;

public class RecogResponse {
    public final String status;
    public final String name;
    public final String reason;

    public RecogResponse(String status, String name, String reason) {
        this.status = status;
        this.name = name;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RecogResponse{" +
                "status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
