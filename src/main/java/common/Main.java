package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mariadb.jdbc.MariaDbDataSource;
import parser.JsonParser;
import parser.Parser;
import dto.ReceiptDto;
import service.ReceiptService;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String ROOT_FOLDER = "C:/Users/Prodi/Downloads/";
    private static final String FILENAME = "file.json";
    private static final MariaDbDataSource DATA_SOURCE = new MariaDbDataSource();
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/groceries";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "147896";

    static {
        try {
            DATA_SOURCE.setUrl(DB_URL);
            DATA_SOURCE.setUser(DB_USERNAME);
            DATA_SOURCE.setPassword(DB_PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("An error occurred while trying to connect the database", e);
        }
    }

    public static void main(String[] args) {
        Parser parser = new JsonParser();
        ReceiptDto receiptDto = parser.parse(Paths.get(ROOT_FOLDER + FILENAME));

        ReceiptService receiptService = new ReceiptService(DATA_SOURCE);
        receiptService.add(receiptDto);
    }
}
