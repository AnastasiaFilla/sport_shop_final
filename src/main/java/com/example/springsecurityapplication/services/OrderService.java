package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.enumm.Status;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByPerson(Person person) {
        return orderRepository.findByPerson(person);
    }

    public Optional<Order> getOrderByLastFourChars(String lastFourChars) {
        return orderRepository.findByLastFourChars(lastFourChars);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
      Optional<Order> optionalOrder = orderRepository.findById(id);
      return optionalOrder.orElse(null);
    }

    @Transactional
    public void updateOrder(int id, Order order){
      order.setId(id);
      orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(int id) {
      orderRepository.deleteById(id);
    }

    @Transactional
    public void updateOrderStatus(int orderId, Status newStatus) {
      Optional<Order> optionalOrder = orderRepository.findById(orderId);
      if (optionalOrder.isPresent()) {
          Order order = optionalOrder.get();
          order.setStatus(newStatus);
          orderRepository.save(order);
      }
  }
}
