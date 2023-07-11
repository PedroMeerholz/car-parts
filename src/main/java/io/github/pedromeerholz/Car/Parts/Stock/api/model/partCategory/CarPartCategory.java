package io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CarPartCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
