package test.com.cgptex.order.service;

import com.ecom.api.orderservice.model.Order;
import com.ecom.api.orderservice.model.OrderItem;
import com.ecom.api.orderservice.repository.OrderRepository;
import com.ecom.api.orderservice.command.OrderCommandService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Test
    public void testCreateOrder() {
        OrderRepository mockRepo = Mockito.mock(OrderRepository.class);
        OrderCommandService service = new OrderCommandService(mockRepo);

        Order order = new Order();
        order.setUserId("user123");
        when(mockRepo.save(any(Order.class))).thenReturn(order);

        List<OrderItem> items = List.of(new OrderItem("p1", 2));
        Order created = service.createOrder("user123", items);

        assertEquals("user123", created.getUserId());
    }
}