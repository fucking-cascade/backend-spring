package service1.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Entity {
    @Id
    @NotNull
    private int id;

    @NotNull
    private String name;

    @NotNull
    private Type type;

    public Entity() {}

    public Entity(int id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + this.id +
                ", name='" + this.name + "\'" +
                ", type=" + this.type.toString() +
                        "}";
    }
}
