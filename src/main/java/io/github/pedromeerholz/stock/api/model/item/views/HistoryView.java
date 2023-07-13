package io.github.pedromeerholz.stock.api.model.item.views;

import jakarta.persistence.*;

@Entity
@Table(name = "historyview")
public class HistoryView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyid")
    private Long historyId;
    @Column(name = "itemid")
    private Long itemId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "category")
    private String category;
    @Column(name = "date")
    private String date;
    @Column(name = "enabled")
    private boolean enabled;

    public Long getHistoryId() {
        return historyId;
    }

    public Long getItemId() {
        return itemId;
    }

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

    public String getDate() {
        return date;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
