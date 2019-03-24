package com.changmaidman.scarlet.repository;

import com.changmaidman.scarlet.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findByName(String name);

    Optional<Users> findById(Integer id);
}
