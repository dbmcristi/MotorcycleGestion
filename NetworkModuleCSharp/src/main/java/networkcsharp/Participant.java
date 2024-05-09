package networkcsharp;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Participant extends Entity<Integer> implements Serializable {
    private int id;
    private String teamName;
    private String name;
    private int idRace;
    private static final long serialVersionUID = 1L;
    public Participant(int id, String teamName, String name, int idRace) {
        super(id);
        this.id = id;
        this.teamName = teamName;
        this.name = name;
        this.idRace = idRace;
    }

    public Participant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdRace() {
        return idRace;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", name='" + name + '\'' +
                ", idRace=" + idRace +
                '}';
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(id);
        stream.writeObject(teamName);
        stream.writeObject(name);
        stream.writeObject(idRace);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        id = (int) stream.readObject();
        teamName = (String) stream.readObject();
        name = (String) stream.readObject();
        idRace = (int) stream.readObject();
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new UnsupportedOperationException("readObjectNoData is not supported yet.");
    }
}

