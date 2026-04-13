package ui;

import java.util.function.Function;

public class ColumnDefinition<T> {
    private final String name;
    private final Function<T, Object> valueProvider;

    public ColumnDefinition(String name, Function<T, Object> valueProvider) {
        this.name = name;
        this.valueProvider = valueProvider;
    }

    public String getName() {
        return name;
    }

    public Object getValue(T object) {
        return valueProvider.apply(object);
    }
}