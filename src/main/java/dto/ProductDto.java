package dto;

import java.util.Objects;

/**
 * @author Vladislav Konovalov
 */
public class ProductDto extends AbstractDto implements Comparable<ProductDto> {
    private final String name;
    private final double price;

    public ProductDto(String name, double price) {
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
    public int compareTo(ProductDto that) {
        int result = this.getName().compareTo(that.getName());
        if (result != 0) {
            return result;
        } else {
            return Double.compare(this.getPrice(), that.getPrice());
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ProductDto that = (ProductDto) object;
        return Double.compare(that.price, price) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
