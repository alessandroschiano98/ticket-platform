package org.lessons.java.spring.ticket_platform.repository;

import java.util.List;

import org.lessons.java.spring.ticket_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRolesNameIn(List<String> roleNames);
}
