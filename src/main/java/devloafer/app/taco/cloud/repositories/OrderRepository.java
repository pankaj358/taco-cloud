package devloafer.app.taco.cloud.repositories;

import devloafer.app.taco.cloud.domains.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
