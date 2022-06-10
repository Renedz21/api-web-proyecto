package com.grupo6.proyecto.grupo6.auth;

import com.grupo6.proyecto.grupo6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginDao extends JpaRepository<User, Long> {

    public User findByUsername(String usuario);
}
