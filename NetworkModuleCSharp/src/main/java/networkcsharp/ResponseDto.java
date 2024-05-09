package networkcsharp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class ResponseDto implements IResponse, Serializable {
    private String responseType;
    private String message;
    private List<Participant> participants;
    private static final long serialVersionUID = 1L;
    public ResponseDto() {
    }

    public ResponseDto(String responseType, String message, List<Participant> participants) {
        this.responseType = responseType;
        this.message = message;
        this.participants = participants;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getMessage() {
        return message;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "responseType='" + responseType + '\'' +
                ", message='" + message + '\'' +
                ", participants=" + participants +
                '}';
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(responseType);
        stream.writeObject(message);
        stream.writeObject(participants);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        responseType = (String) stream.readObject();
        message = (String) stream.readObject();
        participants = (List<Participant>) stream.readObject();
    }
}