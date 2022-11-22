package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mariadb.jdbc.MariaDbDataSource;
import parser.impl.JsonParser;
import parser.api.Parser;
import dto.ReceiptDto;
import service.api.ReceiptService;
import service.impl.ReceiptServiceImpl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + "/Downloads/";
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
            LOGGER.error("An error occurred while trying to connect to the database", e);
        }
    }

    public static void main(String[] args) {
        Path pathToInputFile = Paths.get(DOWNLOAD_FOLDER + FILENAME);
        Parser parser = new JsonParser();
        ReceiptDto receiptDto = parser.parse(pathToInputFile);

        ReceiptService receiptService = new ReceiptServiceImpl(DATA_SOURCE);
        receiptService.add(receiptDto);
    }
}
