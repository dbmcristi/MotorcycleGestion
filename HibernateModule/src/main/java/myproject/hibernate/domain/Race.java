package myproject.hibernate.domain;
import jakarta.persistence.*;

import java.util.Set;

@Table(name="Race")
@Entity
public class Race implements IEntity<Integer> {
    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "engineCapacity")
    String engineCapacity;
    @OneToMany(mappedBy="id")
    private Set<Participant> items;
    public Race() {

    }

    public Race( String name, String engineCapacity) {
        this.name = name;
        this.engineCapacity = engineCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name=" + name +
                ", engineCapacity=" + engineCapacity +
                "}\n";
    }

    public Set<Participant> getItems() {
        return items;
    }

    public void setItems(Set<Participant> items) {
        this.items = items;
    }
}
