package dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vladislav Konovalov
 */
class ReceiptDtoTest {
    @Test
    void testAddProduct_NewProduct() {
        ReceiptDto receipt = new ReceiptDto("Seller", LocalDateTime.now());
        ProductDto product = new ProductDto("Product", 0.1);
        double quantity = 1;

        receipt.addProduct(product, quantity);

        String message = "Should add the product and its quantity to the receipt";
        assertEquals(quantity, receipt.getProductQuantityMap().get(product), message);
    }

    @Test
    void testAddProduct_ExistingProduct() {
        ReceiptDto receipt = new ReceiptDto("Seller", LocalDateTime.now());
        ProductDto product = new ProductDto("Product", 0.1);
        double quantity1 = 1;
        receipt.addProduct(product, quantity1);
        double quantity2 = 3.6;

        receipt.addProduct(product, quantity2);

        String message = "Should add the quantity of the existing product in the receipt";
        assertEquals(quantity1 + quantity2, receipt.getProductQuantityMap().get(product), message);
    }
}