package com.example.CoffeeShop.generators;

import com.example.CoffeeShop.entity.order.Order;
import com.example.CoffeeShop.services.EventService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
/**
 * Класс для кастомной генерации заказов с случайными данными
 */
@Component
public class OrderGenerator {
    private final Random random = new Random();
    private EventService eventService;
    private int orderId;

    public OrderGenerator(EventService eventService) {
        this.eventService = eventService;
    }

    public Order generateOrder() {

        orderId = eventService.findMaxOrderId();
        orderId++;
        return new Order.Builder()
                .withId(orderId)
                .withCustomerId(UUID.randomUUID())
                .withEmployeeId(UUID.randomUUID())
                .withDeliveryTime(LocalDateTime.now().plusMinutes(random.nextInt(10,40)))
                .withProductId(UUID.randomUUID())
                .withProductCost(random.nextLong(50,200))
                .build();
    }

}
