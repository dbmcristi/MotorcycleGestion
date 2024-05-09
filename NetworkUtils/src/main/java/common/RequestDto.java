package common;

import domain.Participant;

import java.io.Serializable;

public class RequestDto implements Serializable {

    private final String requestType;
    private final Participant dto;

    public RequestDto(String requestType, Participant dto) {
        this.requestType = requestType;
        this.dto = dto;
    }

    public String getRequestType() {
        return requestType;
    }

    public Participant getDto() {
        return dto;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "requestType='" + requestType + '\'' +
                ", dto=" + dto +
                '}';
    }
}
