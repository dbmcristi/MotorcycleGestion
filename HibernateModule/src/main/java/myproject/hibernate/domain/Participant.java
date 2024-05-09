package myproject.hibernate.domain;


import jakarta.persistence.*;

@Table(name = "Participant")
@Entity
public class Participant implements IEntity<Integer> {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "id")
    Integer id;
    @Column(name = "teamName")
    String teamName;
    @Column(name = "name")
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRace", nullable = false)
    Race race;

    public Participant(String teamName, String name) {
        this.teamName = teamName;
        this.name = name;
    }

    public Participant() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }


    @Override
    public String toString() {
        return race != null ? "Participant{" +
                "id=" + id + '\'' +
                "name='" + name + '\'' +
                ", teamName='" + teamName + '\'' +
                 ", idRace=" + race.getId() +
                '}':
                "Participant " +
                "id=" + id + '\'' +
                "name='" + name + '\'' +
                ", teamName='" + teamName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
