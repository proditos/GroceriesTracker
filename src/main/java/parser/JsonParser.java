package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Order;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class JsonParser implements Parser {
    private final ObjectMapper objectMapper;

    public JsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Order> parseOrder(Path path) {
        Optional<Order> optional = Optional.empty();
        try (InputStream is = Files.newInputStream(path)) {
            optional = Optional.of(objectMapper.readValue(is, Order.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optional;
    }
}
