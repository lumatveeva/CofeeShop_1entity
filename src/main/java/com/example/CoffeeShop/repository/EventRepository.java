package com.example.CoffeeShop.repository;

import com.example.CoffeeShop.entity.event.EventType;
import com.example.CoffeeShop.entity.event.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<OrderEvent, UUID> {
    Optional<OrderEvent> findTopByOrderIdOrderByTimeStampDesc(int orderId);
    Optional<OrderEvent> findByOrderIdAndEventType(int orderId, EventType eventType);
    List<OrderEvent> findOrderEventByOrderId(int orderId);
    @Query("SELECT MAX(e.orderId) FROM OrderEvent e")
    Optional<Integer> findMaxOrderId();

    @Query("SELECT e.orderId FROM OrderEvent e")
    Set<Integer> findAllOrderId();

}
