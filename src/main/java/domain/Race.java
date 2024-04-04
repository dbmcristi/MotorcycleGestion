package domain;

public class Race extends Entity<Integer> {

    String name;
    EngineCapacity engineCapacity;

    public Race(int id, String name, String engineCapacity) {
        super(id);
        this.name = name;
        this.engineCapacity = EngineCapacity.valueOf(engineCapacity);
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name=" + name +
                ", engineCapacity=" + engineCapacity +
                "}\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EngineCapacity getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(EngineCapacity engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
}
