package parser;

import dto.ProductDto;
import dto.ReceiptDto;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladislav Konovalov
 */
class JsonParserTest {
    private static final String ROOT_FOLDER = "./src/test/resources/";
    private static final String FILENAME = "test_file.json";
    private final Parser jsonParser = new JsonParser();

    @Test
    void testParse() {
        String sellerName = "Seller name";
        LocalDateTime dateTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        ReceiptDto expected = new ReceiptDto(sellerName, dateTime);
        expected.addProduct(new ProductDto("Cheese", 100.27), 1);
        expected.addProduct(new ProductDto("Milk", 200), 0.5);

        ReceiptDto actual = jsonParser.parse(Paths.get(ROOT_FOLDER + FILENAME));

        String notNullMessage = "The DTO should not be null";
        assertNotNull(actual, notNullMessage);
        String sellerNameMessage = "The DTO should have correct seller name";
        assertEquals(expected.getSellerName(), actual.getSellerName(), sellerNameMessage);
        String dateTimeMessage = "The DTO should have correct date and time";
        assertEquals(expected.getDateTime(), actual.getDateTime(), dateTimeMessage);
        String productsMessage = "The DTO should have correct products and their quantities";
        assertEquals(expected.getProductQuantityMap(), actual.getProductQuantityMap(), productsMessage);
    }
}