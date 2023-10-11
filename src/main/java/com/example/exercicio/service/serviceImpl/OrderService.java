package com.example.exercicio.service.serviceImpl;

import com.example.exercicio.entities.Order;
import com.example.exercicio.entities.OrderId;
import com.example.exercicio.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrder(OrderId id) {
        return orderRepository.findById(id).orElse(null);
    }
}
