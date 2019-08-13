package devloafer.app.taco.cloud.repository;

import devloafer.app.taco.cloud.domains.Order;

public interface OrderRepository {

    public Order save(Order order);
}
