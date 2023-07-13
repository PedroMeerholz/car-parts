package io.github.pedromeerholz.Car.Parts.Stock.api.model.part.views;

import jakarta.persistence.*;

@Entity
@Table(name = "partsview")
public class PartsView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partID")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "category")
    private String category;
    @Column(name = "enabled")
    private boolean enabled;

    public Long getId() {
        return id;
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

    public boolean isEnabled() {
        return enabled;
    }
}
