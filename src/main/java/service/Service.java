package service;

import dao.OrderDao;
import dao.OrderProductDao;
import dao.ProductDao;
import entity.Order;
import entity.OrderProduct;
import entity.Product;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class Service {
    private final OrderDao orderDao;
    private final OrderProductDao orderProductDao;
    private final ProductDao productDao;

    public Service(OrderDao orderDao, OrderProductDao orderProductDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderProductDao = orderProductDao;
        this.productDao = productDao;
    }

    public void saveUnique(Order order) {
        if (order == null) return;
        LocalDateTime deliveryDateTime = order.getDeliveryDateTime();
        if (orderDao.findBy(deliveryDateTime).orElse(null) != null) return;
        orderDao.save(order);
        Long orderId = orderDao.findBy(deliveryDateTime).get().getId();

        Map<Long, Double> uniqueProductIds = saveUniqueProducts(order.getProducts());

        uniqueProductIds.forEach((productId, quantity) ->
                orderProductDao.save(new OrderProduct(orderId, productId, quantity)));
    }

    private Map<Long, Double> saveUniqueProducts(Map<Product, Double> products) {
        Map<Long, Double> uniqueIds = new HashMap<>();
        if (products == null) return uniqueIds;
        products.forEach((product, quantity) -> {
            String name = product.getName();
            double price = product.getPrice();
            boolean pricePerKg = product.isPricePerKg();
            Product foundProduct = productDao.findBy(name, price, pricePerKg).orElse(null);
            if (foundProduct != null) {
                uniqueIds.put(foundProduct.getId(), quantity);
            } else {
                productDao.save(product);
                Long productId = productDao.findBy(name, price, pricePerKg).get().getId();
                uniqueIds.put(productId, quantity);
            }
        });
        return uniqueIds;
    }
}
