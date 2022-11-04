package parser;

import dto.ReceiptDto;
import java.nio.file.Path;

/**
 * @author Vladislav Konovalov
 */
public interface Parser {
    ReceiptDto parse(Path path);
}
