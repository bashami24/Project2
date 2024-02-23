package org.example.Model;

import java.util.Objects;
public class Book {
    private static int nextId = 1;
    private int id; // Change from UUID to long
    private String name;
    private double price;
    private int SellerId;

    // Constructors

    public  Book() {

    }

    public Book(String name, double price, int SellerId) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
        this.SellerId = SellerId;
    }


    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }





    // hashCode, equals, and toString methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Double.compare(price, book.price) == 0 && SellerId == book.SellerId && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, SellerId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", SellerId=" + SellerId +
                '}';
    }
}