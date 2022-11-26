package common;

import connection.api.SingletonConnection;
import connection.impl.MariaDbSingletonConnection;
import parser.impl.JsonParser;
import parser.api.Parser;
import dto.ReceiptDto;
import service.api.ReceiptService;
import service.impl.ReceiptServiceImpl;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Vladislav Konovalov
 */
public class Main {
    private static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + "/Downloads/";
    private static final String FILENAME = "file.json";
    private static final Parser PARSER = new JsonParser();
    private static final SingletonConnection SINGLETON_CONNECTION = new MariaDbSingletonConnection();
    private static final ReceiptService RECEIPT_SERVICE = new ReceiptServiceImpl(SINGLETON_CONNECTION);

    public static void main(String[] args) {
        Path pathToInputFile = Paths.get(DOWNLOAD_FOLDER + FILENAME);
        ReceiptDto receiptDto = PARSER.parse(pathToInputFile);
        RECEIPT_SERVICE.add(receiptDto);
        SINGLETON_CONNECTION.close();
    }
}
