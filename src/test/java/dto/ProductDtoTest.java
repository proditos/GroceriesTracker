package dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladislav Konovalov
 */
class ProductDtoTest {
    @Test
    void testHashCode_SameObjects() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);

        String hashCodeMessage = "Hash codes should be the same for the same objects";
        assertEquals(product1.hashCode(), product2.hashCode(), hashCodeMessage);
    }

    @Test
    void testHashCode_DifferentPrice() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.11);

        String hashCodeMessage = "Hash codes should be different for objects with different prices";
        assertNotEquals(product1.hashCode(), product2.hashCode(), hashCodeMessage);
    }

    @Test
    void testHashCode_DifferentName() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Milk", 0.1);

        String hashCodeMessage = "Hash codes should be different for objects with different names";
        assertNotEquals(product1.hashCode(), product2.hashCode(), hashCodeMessage);
    }

    @Test
    void testEquals_Reflexive() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);

        String equalsMessage = "Equals method should be reflexive";
        assertEquals(product1, product1, equalsMessage);
        String hashCodeMessage = "Hash codes should be the same";
        assertEquals(product1.hashCode(), product1.hashCode(), hashCodeMessage);
    }

    @Test
    void testEquals_Symmetric() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);

        String equalsMessage = "Equals method should be symmetric";
        assertTrue(product1.equals(product2) && product2.equals(product1), equalsMessage);
        String hashCodeMessage = "Hash codes should be the same";
        assertEquals(product1.hashCode(), product2.hashCode(), hashCodeMessage);
    }

    @Test
    void testEquals_Transitive() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);
        ProductDto product3 = new ProductDto("Cheese", 0.1);

        boolean equalsCondition = product1.equals(product2) && product2.equals(product3) && product1.equals(product3);
        boolean hashCodeCondition = product1.hashCode() == product2.hashCode() && product2.hashCode() == product3.hashCode() && product1.hashCode() == product3.hashCode();

        String equalsMessage = "Equals method should be transitive";
        assertTrue(equalsCondition, equalsMessage);
        String hashCodeMessage = "Hash codes should be the same";
        assertTrue(hashCodeCondition, hashCodeMessage);
    }

    @Test
    void testEquals_NotEqualsNull() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);

        String equalsMessage = "Equals method should return false if compared to null";
        assertNotEquals(null, product1, equalsMessage);
    }

    @Test
    void testEquals_DifferentPrice() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.11);

        String equalsMessage = "Equals method should return false if the prices of the products are different";
        assertNotEquals(product1, product2, equalsMessage);
    }

    @Test
    void testEquals_DifferentName() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Milk", 0.1);

        String equalsMessage = "Equals method should return false if the names of the products are different";
        assertNotEquals(product1, product2, equalsMessage);
    }

    @Test
    void testEquals_DifferentClass() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        Object product2 = new Object();

        String equalsMessage = "Equals method should return false if the classes of the objects are different";
        assertNotEquals(product1, product2, equalsMessage);
    }

    @Test
    void testCompareTo_Equal() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);

        String message = "Should return zero for equal objects";
        assertEquals(0, product1.compareTo(product2), message);
    }

    @Test
    void testCompareTo_GreaterThenByName() {
        ProductDto product1 = new ProductDto("Milk", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);

        String message = "Should return a positive integer if this object greater than the specified object by name";
        assertTrue(product1.compareTo(product2) > 0, message);
    }

    @Test
    void testCompareTo_GreaterThenByPrice() {
        ProductDto product1 = new ProductDto("Cheese", 0.11);
        ProductDto product2 = new ProductDto("Cheese", 0.1);

        String message = "Should return a positive integer if this object greater than the specified object by price";
        assertTrue(product1.compareTo(product2) > 0, message);
    }

    @Test
    void testCompareTo_LessThenByName() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Milk", 0.1);

        String message = "Should return a negative integer if this object greater than the specified object by name";
        assertTrue(product1.compareTo(product2) < 0, message);
    }

    @Test
    void testCompareTo_LessThenByPrice() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.11);

        String message = "Should return a negative integer if this object greater than the specified object by price";
        assertTrue(product1.compareTo(product2) < 0, message);
    }

    @Test
    void testCompareTo_Null() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);

        String message = "Should throw a NullPointerException when the specified object is null";
        assertThrows(NullPointerException.class, () -> product1.compareTo(null), message);
    }
}