package io.github.pedromeerholz.stock.api.model.itemCategory.dto;

public class ItemCategoryDto {
    private String category;
    private boolean enabled;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
