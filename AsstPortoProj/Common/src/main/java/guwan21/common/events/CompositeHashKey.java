package guwan21.common.events;

import java.util.Objects;

public record CompositeHashKey(Object source, Event.Target target,
                               Event.Type type,
                               Event.Category category,
                                Class<?> targetType, Class<?> sourceType) implements Comparable<CompositeHashKey> {
    public static CompositeHashKey of(Event<?> event) {
        return new CompositeHashKey(
                event.getSource(),
                event.getTarget(),
                event.getType(),
                event.getCategory(),
                event.getTargetType(),
                event.getSourceType()
        );
    }

    public boolean hasSource() {
        return source != null;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public boolean hasType() {
        return type != null;
    }

    public boolean hasCategory() {
        return category != null;
    }

    // Implement equals() and hashCode() for correct comparison and hashing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeHashKey that = (CompositeHashKey) o;
        return source == that.source &&
                target == that.target &&
                type == that.type &&
                category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, type, category);
    }

    @Override
    public int compareTo(CompositeHashKey o) {
        return this.equals(o) ? 0 : 1;
    }
}
