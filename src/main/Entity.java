package main;

import java.util.Objects;

// Корневой абстрактный класс для всех существ и объектов существующих в симуляции.
public abstract class Entity {
    private final EnumObjects type;

    public Entity(EnumObjects type) {
        this.type = type;
    }

    public EnumObjects getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return type == entity.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
