package devloafer.app.taco.cloud.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import devloafer.app.taco.cloud.domains.Order;
import devloafer.app.taco.cloud.domains.Taco;
import devloafer.app.taco.cloud.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate){
      this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
              .withTableName("Taco_Order")
              .usingGeneratedKeyColumns("id");

      this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
              .withTableName("Taco_Order_Tacos");

      this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        for(Taco taco : order.getTacoList())
            saveTacoToOrder(taco, orderId);
        return order;
    }

    private long saveOrderDetails(Order order){
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        long orderId = orderInserter.executeAndReturnKey(values)
                .longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
