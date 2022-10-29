package common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import dao.OrderProductDao;
import dao.ProductDao;
import entity.Order;
import org.mariadb.jdbc.MariaDbDataSource;
import parser.OrderParser;
import parser.Parser;
import service.Service;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class Main {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JSON_ROOT = "C:/Users/Prodi/Downloads/";
    private static final String JSON_FILENAME = "ReceiptJson.json";
    private static final MariaDbDataSource DATA_SOURCE = new MariaDbDataSource();
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/lenta";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "147896";

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            DATA_SOURCE.setUrl(DB_URL);
            DATA_SOURCE.setUser(DB_USERNAME);
            DATA_SOURCE.setPassword(DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parser<Order> parser = new OrderParser(OBJECT_MAPPER);
        Order order = parser.parseJson(Paths.get(JSON_ROOT + JSON_FILENAME)).orElse(null);

        OrderDao orderDao = new OrderDao(DATA_SOURCE);
        OrderProductDao orderProductDao = new OrderProductDao(DATA_SOURCE);
        ProductDao productDao = new ProductDao(DATA_SOURCE);
        Service service = new Service(orderDao, orderProductDao, productDao);
        service.saveUnique(order);
    }
}