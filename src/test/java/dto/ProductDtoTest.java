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

        boolean condition = product1.equals(product2) && product2.equals(product1);

        String equalsMessage = "Equals method should be symmetric";
        assertTrue(condition, equalsMessage);
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

        boolean condition = product1.equals(null);

        String equalsMessage = "Equals method should return false if compared to null";
        assertFalse(condition, equalsMessage);
    }

    @Test
    void testEquals_DifferentPrice() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.11);

        boolean condition = product1.equals(product2);

        String equalsMessage = "Equals method should return false if the prices of the products are different";
        assertFalse(condition, equalsMessage);
    }

    @Test
    void testEquals_DifferentName() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Milk", 0.1);

        boolean condition = product1.equals(product2);

        String equalsMessage = "Equals method should return false if the names of the products are different";
        assertFalse(condition, equalsMessage);
    }

    @Test
    void testEquals_DifferentClass() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        Object product2 = new Object();

        boolean condition = product1.equals(product2);

        String equalsMessage = "Equals method should return false if the classes of the objects are different";
        assertFalse(condition, equalsMessage);
    }

    @Test
    void testCompareTo_Equals() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);

        String message = "CompareTo method should be consistent with the equals method";
        assertEquals(product1.equals(product2), product1.compareTo(product2) == 0, message);
    }

    @Test
    void testCompareTo_Asymmetric() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Milk", 0.1);

        boolean condition = (product1.compareTo(product2)) == (-1 * product2.compareTo(product1));

        String message = "CompareTo method should be asymmetric";
        assertTrue(condition, message);
    }

    @Test
    void testCompareTo_Transitive() {
        ProductDto product1 = new ProductDto("Bread", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);
        ProductDto product3 = new ProductDto("Milk", 0.1);

        int comparison12 = product1.compareTo(product2);
        int comparison23 = product2.compareTo(product3);
        int comparison13 = product1.compareTo(product3);
        boolean absCondition = comparison12 < 0 && comparison23 < 0 && comparison13 < 0;
        boolean descCondition = comparison12 > 0 && comparison23 > 0 && comparison13 > 0;

        String message = "CompareTo method should be transitive";
        assertTrue(absCondition || descCondition, message);
    }

    @Test
    void testCompareTo_BothMoreOrLess() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.1);
        ProductDto product3 = new ProductDto("Milk", 0.1);

        int comparison12 = product1.compareTo(product2);
        int comparison23 = product2.compareTo(product3);
        int comparison13 = product1.compareTo(product3);
        boolean condition = comparison12 == 0 && Integer.signum(comparison13) == Integer.signum(comparison23);

        String message = "If two objects are equal, then they must both be greater or both less than the third object";
        assertTrue(condition, message);
    }

    @Test
    void testCompareTo_SecondField() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);
        ProductDto product2 = new ProductDto("Cheese", 0.11);

        String message = "Comparison should be for all significant fields of the class";
        assertNotEquals(0, product1.compareTo(product2), message);
    }

    @Test
    void testCompareTo_Null() {
        ProductDto product1 = new ProductDto("Cheese", 0.1);

        String message = "Should throw a NullPointerException when the specified object is null";
        assertThrows(NullPointerException.class, () -> product1.compareTo(null), message);
    }
}