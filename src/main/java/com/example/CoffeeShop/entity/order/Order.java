package com.example.CoffeeShop.entity.order;

import com.example.CoffeeShop.entity.event.OrderEvent;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class Order {

    private int id;
    private UUID customerId;
    private UUID employeeId;
    private LocalDateTime deliveryTime;
    private UUID productId;
    private long productCost;
    private LocalDateTime timeStamp = LocalDateTime.now();
    private String cancelledReason;
    private State state;

    private List<OrderEvent> orderEvents;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getProductCost() {
        return productCost;
    }

    public void setProductCost(long productCost) {
        this.productCost = productCost;
    }

    public String getCancelledReason() {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<OrderEvent> getOrderEvents() {
        return orderEvents;
    }

    public void setOrderEvents(List<OrderEvent> orderEvents) {
        this.orderEvents = orderEvents;
    }

    private Order(Order.Builder builder) {
        setId(builder.id);
        setCustomerId(builder.customerId);
        setEmployeeId(builder.employeeId);
        setDeliveryTime(builder.deliveryTime);
        setProductId(builder.productId);
        setProductCost(builder.productCost);
        setCancelledReason(builder.cancelledReason);
        setState(builder.state);
        setOrderEvents(builder.orderEvents);
    }

    public static final class Builder {
        private int id;
        private UUID customerId;
        private UUID employeeId;
        private LocalDateTime deliveryTime;
        private UUID productId;
        private long productCost;
        private String cancelledReason;
        private State state;
        private List<OrderEvent> orderEvents;

        public Builder() {
        }

        public Order.Builder withId(int val) {
            this.id = val;
            return this;
        }
        public Order.Builder withCustomerId(UUID val) {
            this.customerId = val;
            return this;
        }

        public Order.Builder withEmployeeId(UUID val) {
            this.employeeId = val;
            return this;
        }

        public Order.Builder withDeliveryTime(LocalDateTime val) {
            this.deliveryTime = val;
            return this;
        }

        public Order.Builder withProductId(UUID val) {
            this.productId = val;
            return this;
        }

        public Order.Builder withProductCost(long val) {
            this.productCost = val;
            return this;
        }

        public Order.Builder withCancelledReason(String val) {
            this.cancelledReason = val;
            return this;
        }

        public Order.Builder withState(State val) {
            this.state = val;
            return this;
        }
        public Order.Builder withOrderEvents(List<OrderEvent> val) {
            this.orderEvents = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
