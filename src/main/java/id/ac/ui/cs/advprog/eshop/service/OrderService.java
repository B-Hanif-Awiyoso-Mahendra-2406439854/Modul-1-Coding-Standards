package id.ac.ui.cs.advprog.eshop.service;

import java.util.*;

import id.ac.ui.cs.advprog.eshop.model.*;

public interface OrderService {
    public Order createOrder(Order order);
    public Order updateStatus(String orderId, String status);
    public Order findById(String orderId);
    public List<Order> findAllByAuthor(String author);
}
