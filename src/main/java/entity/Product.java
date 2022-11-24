package entity;

/**
 * @author Vladislav Konovalov
 */
public final class Product extends AbstractEntity {
    private final String name;
    private final double price;

    public Product(Long id, String name, double price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price) {
        super(null);
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
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
