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
        int oldSize = receipt.getProductQuantityMap().size();
        double quantity = 1;

        receipt.addProduct(product, quantity);

        String sizeMessage = "Should increase map size by 1";
        assertEquals(oldSize + 1, receipt.getProductQuantityMap().size(), sizeMessage);
        String getMessage = "Should add the product and its quantity to the receipt";
        assertEquals(quantity, receipt.getProductQuantityMap().get(product), getMessage);
    }

    @Test
    void testAddProduct_ExistingProduct() {
        ReceiptDto receipt = new ReceiptDto("Seller", LocalDateTime.now());
        ProductDto product = new ProductDto("Product", 0.1);
        double quantity1 = 1;
        receipt.addProduct(product, quantity1);
        int oldSize = receipt.getProductQuantityMap().size();
        double quantity2 = 3.6;

        receipt.addProduct(product, quantity2);

        String sizeMessage = "Should not increase map size by 1";
        assertEquals(oldSize, receipt.getProductQuantityMap().size(), sizeMessage);
        String getMessage = "Should add the quantity of the existing product in the receipt";
        assertEquals(quantity1 + quantity2, receipt.getProductQuantityMap().get(product), getMessage);
    }
}