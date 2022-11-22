package parser.api;

import dto.ReceiptDto;
import java.nio.file.Path;

/**
 * The interface that defines the behavior of the receipt parser.
 * The file is submitted to the input.
 * At the output, you need to get a filled receipt DTO or null.
 *
 * @see ReceiptDto
 *
 * @author Vladislav Konovalov
 */
public interface Parser {
    /**
     * The method that parses the file and initializes and returns the DTO based on the received data.
     *
     * @param path the file path.
     * @return null if the file could not be parsed.
     * In all other cases, returns the parsed and initialized DTO.
     */
    ReceiptDto parse(Path path);
}
