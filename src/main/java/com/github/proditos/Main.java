package com.github.proditos;

import com.github.proditos.connection.api.SingletonConnection;
import com.github.proditos.connection.impl.MariaDbSingletonConnection;
import com.github.proditos.dao.api.ProductDao;
import com.github.proditos.dao.api.ReceiptDao;
import com.github.proditos.dao.api.ReceiptProductDao;
import com.github.proditos.parser.impl.JsonParser;
import com.github.proditos.service.impl.ReceiptServiceImpl;
import com.github.proditos.dao.impl.ProductDaoImpl;
import com.github.proditos.dao.impl.ReceiptDaoImpl;
import com.github.proditos.dao.impl.ReceiptProductDaoImpl;
import com.github.proditos.dto.ReceiptDto;
import com.github.proditos.parser.api.Parser;
import com.github.proditos.service.api.ReceiptService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

/**
 * @author Vladislav Konovalov
 */
public final class Main {
    private static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + "/Downloads/";
    private static final String FILENAME = "file.json";

    private Main() {
    }

    public static void main(String[] args) {
        Path pathToInputFile = Paths.get(DOWNLOAD_FOLDER + FILENAME);
        Parser parser = new JsonParser();
        ReceiptDto receiptDto = parser.parse(pathToInputFile);
        if (receiptDto == null) {
            return;
        }
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
    }
}
