package entity;

import java.util.Objects;

/**
 * @author Vladislav Konovalov
 */
public class Product {
    private Long id;
    private String name;
    private double price;
    private boolean pricePerKg;

    public Product(Long id, String name, double price, boolean pricePerKg) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pricePerKg = pricePerKg;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPricePerKg() {
        return pricePerKg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                pricePerKg == product.pricePerKg &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, pricePerKg);
    }
}
