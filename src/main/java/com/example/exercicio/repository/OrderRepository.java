package com.example.exercicio.repository;

import com.example.exercicio.entities.Order;
import com.example.exercicio.entities.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
