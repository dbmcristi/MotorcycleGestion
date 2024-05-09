package common;

import domain.Participant;

import java.io.Serializable;
import java.util.List;

public class ResponseDto implements Serializable {

    private final String responseType;
    private final String message;

    private final List<Participant> participants;

    public ResponseDto(String responseType, String message, List<Participant> participants) {
        this.responseType = responseType;
        this.message = message;
        this.participants = participants;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                "participants='" + participants + '\'' +
                '}';
    }
}
