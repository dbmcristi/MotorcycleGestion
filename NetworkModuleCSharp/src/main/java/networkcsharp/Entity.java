package networkcsharp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public Entity() {
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(id);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        id = (ID) stream.readObject();
    }
}
