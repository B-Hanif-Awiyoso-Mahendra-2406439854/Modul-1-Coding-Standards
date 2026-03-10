package id.ac.ui.cs.advprog.eshop.service;

import java.util.*;

import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.repository.*;

public class OrderServiceImpl implements OrderService {
    @AutoWired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Order updateStatus(String orderId, String status) {
        return null;
    }

    @Override
    public List<Order> findAllByAuthor(String author) {
        return null;
    }

    @Override
    public Order findById(String orderId) {
        return null;
    }
    
}
