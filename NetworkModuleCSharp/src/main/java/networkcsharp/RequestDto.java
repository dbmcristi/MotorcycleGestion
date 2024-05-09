package networkcsharp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RequestDto implements IRequest, Serializable {
    private String requestType;
    private Participant dto;
    private static final long serialVersionUID = 1L;
    public RequestDto() {
    }

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

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(requestType);
        stream.writeObject(dto);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        requestType = (String) stream.readObject();
        dto = (Participant) stream.readObject();
    }
}

