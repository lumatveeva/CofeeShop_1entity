package com.example.CoffeeShop.exception;

/**
 * Исключение при неуспешной попытке поиска событий заказа
 */
public class OrderEventNotFoundException extends RuntimeException{
    public OrderEventNotFoundException(String message) {
        super(message);
    }
}
