package common;

import connection.api.SingletonConnection;
import connection.impl.MariaDbSingletonConnection;
import dao.api.ProductDao;
import dao.api.ReceiptDao;
import dao.api.ReceiptProductDao;
import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import dao.impl.ReceiptProductDaoImpl;
import parser.impl.JsonParser;
import parser.api.Parser;
import dto.ReceiptDto;
import service.api.ReceiptService;
import service.impl.ReceiptServiceImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

/**
 * @author Vladislav Konovalov
 */
public class Main {
    private static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + "/Downloads/";
    private static final String FILENAME = "file.json";

    public static void main(String[] args) throws IOException {
        Path pathToInputFile = Paths.get(DOWNLOAD_FOLDER + FILENAME);
        Parser parser = new JsonParser();
        ReceiptDto receiptDto = parser.parse(pathToInputFile);

        SingletonConnection singletonConnection = new MariaDbSingletonConnection();
        Connection connection = singletonConnection.getInstance();
        if (connection != null) {
            ReceiptDao receiptDao = new ReceiptDaoImpl(connection);
            ReceiptProductDao receiptProductDao = new ReceiptProductDaoImpl(connection);
            ProductDao productDao = new ProductDaoImpl(connection);
            ReceiptService receiptService = new ReceiptServiceImpl(
                    singletonConnection,
                    receiptDao,
                    receiptProductDao,
                    productDao);
            receiptService.add(receiptDto);
            singletonConnection.close();
        }

        System.out.println("\nPress Enter to continue...");
        System.in.read();
    }
}
