package parser;

import entity.Order;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface Parser {
    Optional<Order> parseOrder(Path path);
}
