package parser;

import java.nio.file.Path;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface Parser<T> {
    Optional<T> parseJson(Path path);
}
