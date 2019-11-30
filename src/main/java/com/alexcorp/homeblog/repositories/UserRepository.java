package com.alexcorp.homeblog.repositories;

import com.alexcorp.homeblog.domais.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
