package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  // Метод чтоб найти все заказы
  List<Order> findAll();

  // Метод чтоб найти заказы по персоне
  List<Order> findByPerson(Person person);

  // Метод чтоб найти заказы по последним 4 символам номера заказа
  @Query("SELECT o FROM Order o WHERE SUBSTRING(o.number, LENGTH(o.number) - 3) = :lastFourChars")
  Optional<Order> findByLastFourChars(@Param("lastFourChars") String lastFourChars);

  Optional<Order> findById(int id);
}
