package org.example;


import javax.persistence.*;
import java.lang.annotation.Target;

@Entity
@Table (name = "Menu")
public class MenuClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


@Column(nullable = false)
    private String dish;
    private int discount;

    private int price;
    private int weight;

    public MenuClient (){}

    public MenuClient(String dish, int price,  int weight, int discount) {
        this.dish = dish;
        this.discount = discount;
        this.price = price;
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "MenuClient{" +
                "id=" + id +
                ", dish='" + dish + '\'' +
                ", discount='" + discount + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }
}
