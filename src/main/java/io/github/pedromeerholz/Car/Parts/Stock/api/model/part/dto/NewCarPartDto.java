package io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto;

public class NewCarPartDto {
    private String name;
    private String description;
    private int quantity;
    private String category;
    private boolean enabled;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
