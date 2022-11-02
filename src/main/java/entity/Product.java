package entity;

import java.util.Objects;

/**
 * @author Vladislav Konovalov
 */
public class Product extends AbstractEntity implements Comparable<Product> {
    private final String name;
    private final double price;

    public Product(Long id, String name, double price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public int compareTo(Product product) {
        return this.getName().compareTo(product.getName());
    }
}
