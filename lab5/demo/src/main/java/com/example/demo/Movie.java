package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    // Пустий конструктор обов'язковий для Hibernate
    public Movie() {}

    // ГЕТТЕРИ (Jackson використовує їх, щоб створити JSON)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }

    // СЕТТЕРИ (Hibernate використовує їх, щоб записати дані з бази в об'єкт)
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}