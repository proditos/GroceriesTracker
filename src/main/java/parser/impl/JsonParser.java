package parser.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ProductDto;
import dto.ReceiptDto;
import exception.ParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.api.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * @author Vladislav Konovalov
 */
public class JsonParser implements Parser {
    private static final Logger LOGGER = LogManager.getLogger(JsonParser.class);
    private static final String NULL_ERROR_MSG = "Input JsonNode is null";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonParser() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ReceiptDto parse(Path path) {
        if (path == null) {
            return null;
        }
        try (InputStream is = Files.newInputStream(path)) {
            JsonNode rootNode = objectMapper.readTree(is);
            String sellerName = parseSellerName(rootNode);
            LocalDateTime dateTime = parseDateTime(rootNode);
            ReceiptDto receipt = new ReceiptDto(sellerName, dateTime);
            Iterator<JsonNode> productNodes = parseProducts(rootNode);
            while (productNodes.hasNext()) {
                JsonNode productNode = productNodes.next();
                ProductDto product = parseProduct(productNode);
                double quantity = parseQuantity(productNode);
                receipt.addProduct(product, quantity);
            }
            return receipt;
        } catch (ParserException | IOException e) {
            LOGGER.error("An error occurred while parsing the json file", e);
            return null;
        }
    }

    private String parseSellerName(JsonNode rootNode) {
        assert rootNode != null : NULL_ERROR_MSG;
        JsonNode sellerNameNode = rootNode.findPath("receipt").path("user");
        if (sellerNameNode.isMissingNode()) {
            throw new ParserException("The seller's name is missing");
        } else {
            return sellerNameNode.asText();
        }
    }

    private LocalDateTime parseDateTime(JsonNode rootNode) {
        assert rootNode != null : NULL_ERROR_MSG;
        JsonNode dateTimeNode = rootNode.findPath("receipt").path("dateTime");
        if (dateTimeNode.isMissingNode()) {
            throw new ParserException("The receipt datetime is missing");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(dateTimeNode.asText(), formatter);
        }
    }

    private Iterator<JsonNode> parseProducts(JsonNode rootNode) {
        assert rootNode != null : NULL_ERROR_MSG;
        JsonNode productsNode = rootNode.findPath("items");
        if (productsNode.isMissingNode()) {
            throw new ParserException("Products are missing");
        } else {
            return productsNode.iterator();
        }
    }

    private ProductDto parseProduct(JsonNode productNode) {
        assert productNode != null : NULL_ERROR_MSG;
        JsonNode nameNode = productNode.path("name");
        if (nameNode.isMissingNode()) {
            throw new ParserException("The product name is missing");
        }
        JsonNode priceNode = productNode.path("price");
        if (priceNode.isMissingNode()) {
            throw new ParserException("The product price is missing");
        }
        return new ProductDto(nameNode.asText(), priceNode.asInt() / 100.0);
    }

    private double parseQuantity(JsonNode productNode) {
        assert productNode != null : NULL_ERROR_MSG;
        JsonNode quantityNode = productNode.path("quantity");
        if (quantityNode.isMissingNode()) {
            throw new ParserException("The product quantity is missing");
        }
        return quantityNode.asDouble();
    }
}
