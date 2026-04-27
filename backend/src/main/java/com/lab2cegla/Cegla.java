package com.lab2cegla;

public class Cegla {
    private int id; // Додали ID для CRUD
    private String name;
    private String mark;
    private String type;
    private double price;

    public Cegla() {} // Пустий конструктор для Gson

    public Cegla(int id, String name, String mark, String type, double price) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.type = type;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMark() { return mark; }
    public void setMark(String mark) { this.mark = mark; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}