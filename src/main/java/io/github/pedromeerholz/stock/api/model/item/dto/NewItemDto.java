package io.github.pedromeerholz.stock.api.model.item.dto;

public class NewItemDto {
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
