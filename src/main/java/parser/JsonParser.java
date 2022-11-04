package parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ProductDto;
import dto.ReceiptDto;
import exception.ParserException;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonParser() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ReceiptDto parse(Path path) {
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
            e.printStackTrace();
        }
        return null;
    }

    private String parseSellerName(JsonNode rootNode) {
        JsonNode sellerNameNode = rootNode.findPath("seller").path("name");
        if (sellerNameNode.isMissingNode()) {
            throw new ParserException("The seller's name is missing");
        } else {
            return sellerNameNode.asText();
        }
    }

    private LocalDateTime parseDateTime(JsonNode rootNode) {
        JsonNode dateTimeNode = rootNode.findPath("query").path("date");
        if (dateTimeNode.isMissingNode()) {
            throw new ParserException("The receipt datetime is missing");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(dateTimeNode.asText(), formatter);
        }
    }

    private Iterator<JsonNode> parseProducts(JsonNode rootNode) {
        JsonNode productsNode = rootNode.findPath("items");
        if (productsNode.isMissingNode()) {
            throw new ParserException("Products are missing");
        } else {
            return productsNode.iterator();
        }
    }

    private ProductDto parseProduct(JsonNode productNode) {
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
        JsonNode quantityNode = productNode.path("quantity");
        if (quantityNode.isMissingNode()) {
            throw new ParserException("The product quantity is missing");
        }
        return quantityNode.asDouble();
    }
}
