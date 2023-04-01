package com.github.proditos.parser.impl;

import com.github.proditos.dto.ProductDto;
import com.github.proditos.dto.ReceiptDto;
import com.github.proditos.parser.api.Parser;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladislav Konovalov
 */
class JsonParserTest {
    private static final String NULL_DTO_MESSAGE = "The DTO should be null";
    private static final String ROOT_FOLDER = "src/test/resources/com/github/proditos/parser/impl/";
    private static final String CORRECT_FILE_NAME = "correct_file.json";
    private static final String INCORRECT_FILE_NAME = "incorrect_file.json";
    private static final String MISSING_DATETIME_FILE_NAME = "missing_datetime.json";
    private static final String MISSING_PRODUCT_NAME_FILE_NAME = "missing_product_name.json";
    private static final String MISSING_PRODUCT_PRICE_FILE_NAME = "missing_product_price.json";
    private static final String MISSING_PRODUCT_QUANTITY_FILE_NAME = "missing_product_quantity.json";
    private static final String MISSING_PRODUCTS_FILE_NAME = "missing_products.json";
    final Parser jsonParser = new JsonParser();

    @Test
    void testParse_CorrectFile() {
        String sellerName = "Seller name";
        LocalDateTime dateTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        ReceiptDto expected = new ReceiptDto(sellerName, dateTime);
        expected.addProduct(new ProductDto("Cheese", 100.27), 1);
        expected.addProduct(new ProductDto("Milk", 200), 0.5);

        ReceiptDto actual = jsonParser.parse(Paths.get(ROOT_FOLDER + CORRECT_FILE_NAME));

        String notNullMessage = "The DTO should not be null";
        assertNotNull(actual, notNullMessage);
        String sellerNameMessage = "The DTO should have correct seller name";
        assertEquals(expected.getSellerName(), actual.getSellerName(), sellerNameMessage);
        String dateTimeMessage = "The DTO should have correct date and time";
        assertEquals(expected.getDateTime(), actual.getDateTime(), dateTimeMessage);
        String productsMessage = "The DTO should have correct products and their quantities";
        assertEquals(expected.getProductQuantityMap(), actual.getProductQuantityMap(), productsMessage);
    }

    @Test
    void testParse_IncorrectFile() {
        ReceiptDto receiptDto = jsonParser.parse(Paths.get(ROOT_FOLDER + INCORRECT_FILE_NAME));

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }

    @Test
    void testParse_MissingDatetimeFile() {
        ReceiptDto receiptDto = jsonParser.parse(Paths.get(ROOT_FOLDER + MISSING_DATETIME_FILE_NAME));

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }

    @Test
    void testParse_MissingProductNameFile() {
        ReceiptDto receiptDto = jsonParser.parse(Paths.get(ROOT_FOLDER + MISSING_PRODUCT_NAME_FILE_NAME));

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }

    @Test
    void testParse_MissingProductPriceFile() {
        ReceiptDto receiptDto = jsonParser.parse(Paths.get(ROOT_FOLDER + MISSING_PRODUCT_PRICE_FILE_NAME));

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }

    @Test
    void testParse_MissingProductQuantityFile() {
        ReceiptDto receiptDto = jsonParser.parse(Paths.get(ROOT_FOLDER + MISSING_PRODUCT_QUANTITY_FILE_NAME));

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }

    @Test
    void testParse_MissingProductsFile() {
        ReceiptDto receiptDto = jsonParser.parse(Paths.get(ROOT_FOLDER + MISSING_PRODUCTS_FILE_NAME));

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }

    @Test
    void testParse_PathIsNull() {
        ReceiptDto receiptDto = jsonParser.parse(null);

        assertNull(receiptDto, NULL_DTO_MESSAGE);
    }
}
