package com.example.CoffeeShop.services;

import com.example.CoffeeShop.entity.event.EventType;
import com.example.CoffeeShop.entity.event.OrderEvent;
import com.example.CoffeeShop.entity.order.Order;
import com.example.CoffeeShop.entity.order.State;
import com.example.CoffeeShop.exception.OrderEventNotFoundException;
import com.example.CoffeeShop.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Сервис для создания событий по изменению статуса заказа
 */
@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Метод для получения заказа по его id, поля заказа устанавливаются согласно имеющимся событиям
     * @param orderId идентификатор заказа
     * @return объект Order
     * @throws OrderEventNotFoundException Если не существует событий по данному заказу
     */
    public Order getOrderByOrderId(int orderId) {
        OrderEvent orderEventRegistered = eventRepository.findByOrderIdAndEventType(orderId, EventType.ORDER_REGISTERED)
                .orElseThrow(() -> new OrderEventNotFoundException("События по заказу " + orderId + " не найдены"));
        OrderEvent latestOrderEvent = eventRepository.findTopByOrderIdOrderByTimeStampDesc(orderId)
                .orElseThrow(() -> new OrderEventNotFoundException("События по заказу " + orderId + " не найдены"));
        List<OrderEvent> orderEvents = eventRepository.findOrderEventByOrderId(orderId);
        Order order = new Order.Builder()
                .withId(orderEventRegistered.getOrderId())
                .withEmployeeId(orderEventRegistered.getEmployeeId())
                .withCustomerId(orderEventRegistered.getCustomerId())
                .withDeliveryTime(orderEventRegistered.getDeliveryTime())
                .withProductId(orderEventRegistered.getProductId())
                .withProductCost(orderEventRegistered.getProductCost())
                .withState(fromEventTypeToState(latestOrderEvent.getEventType()))
                .withCancelledReason(latestOrderEvent.getCancelledReason())
                .withOrderEvents(orderEvents)
                .build();
        return order;
    }

    /**
     *Метод для сохранения нового события
     * @param event Новое событие
     */
    public void save(OrderEvent event) {
        eventRepository.save(event);
    }

    /**
     * Метод для создания нового события с статусом 'Заказ зарегистрирован'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent registrationsEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order.getId())
                .withEventType(EventType.ORDER_REGISTERED)
                .withCustomerId(order.getCustomerId())
                .withDeliveryTime(order.getDeliveryTime())
                .withEmployeeId(order.getEmployeeId())
                .withProductId(order.getProductId())
                .withProductCost(order.getProductCost())
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ отменен'
     * @param order Заказ, статус которого был изменен
     * @param cancelledReason Причина для отмены заказа
     * @return объект OrderEvent
     */
    public OrderEvent cancelledEvent(Order order, String cancelledReason) {
        return new OrderEvent.Builder()
                .withOrder(order.getId())
                .withEmployeeId(order.getEmployeeId())
                .withCancelledReason(cancelledReason)
                .withEventType(EventType.ORDER_CANCELLED)
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ взят в работу'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent acceptedEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order.getId())
                .withEmployeeId(order.getEmployeeId())
                .withEventType(EventType.ORDER_ACCEPTING)
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ готов к выдаче'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent readyToDeliveryEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order.getId())
                .withEmployeeId(order.getEmployeeId())
                .withEventType(EventType.ORDER_READY_TO_DELIVERY)
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ выдан'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent deliverEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order.getId())
                .withEmployeeId(order.getEmployeeId())
                .withEventType(EventType.ORDER_DELIVERED)
                .build();
    }

    /**
     * Преобразует тип события EventType в соответствующее состояние заказа State.
     *
     * @param eventType тип события, который нужно преобразовать
     * @return статус заказа, соответствующий переданному типу события
     * @throws IllegalStateException если переданный тип события не найден
     */
    private State fromEventTypeToState(EventType eventType) {
        switch (eventType) {
            case ORDER_REGISTERED -> {
                return State.REGISTER;
            }
            case ORDER_ACCEPTING -> {
                return State.ACCEPT;
            }
            case ORDER_READY_TO_DELIVERY -> {
                return State.READY_TO_DELIVERY;
            }
            case ORDER_DELIVERED -> {
                return State.DELIVERED;
            }
            case ORDER_CANCELLED -> {
                return State.CANCELLED;
            }
            default -> throw new IllegalStateException("Тип события " + " не найден");
        }
    }

    /**
     * Метод для нахождения id последнего заказа
     * @return Идентификатор заказа
     */
    public Integer findMaxOrderId(){
        return eventRepository.findMaxOrderId().orElse(0);
    }

    public List<Order> findAllOrder() {
        Set<Integer> allOrderId = eventRepository.findAllOrderId();
        List<Order> orderList = new ArrayList<>();
        for(Integer orderId: allOrderId){
            orderList.add(getOrderByOrderId(orderId));
        }
        return orderList;
    }
}
