package io.github.pedromeerholz.Car.Parts.Stock.api.model.part;

import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyid")
    private Long historyId;
    @Column(name = "partid")
    private Long partId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "category")
    private Long category;
    @Column(name = "date")
    private String date;
    @Column(name = "enabled")
    private boolean enabled;
}
