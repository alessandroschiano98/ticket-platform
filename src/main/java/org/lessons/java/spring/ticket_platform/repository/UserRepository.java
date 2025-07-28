package org.lessons.java.spring.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring.ticket_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRolesNameIn(List<String> roleNames);
    Optional<User> findByUsername(String username);
    List<User> findByRolesNameInAndFullNameContainingIgnoreCase(List<String> roles, String fullName);
}

