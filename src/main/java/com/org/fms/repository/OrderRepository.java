package com.org.fms.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.org.fms.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderNumber(String orderNumber);
}
