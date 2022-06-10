package com.grupo6.proyecto.grupo6.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.grupo6.proyecto.grupo6.entity.User;


public interface IUserDao extends JpaRepository<User, Long>{

    Page<User> findAllByType(Pageable pageable,String dato);

    @Query(value = "SELECT count(u) from users u")
    Integer cantidadUser();

    Integer countByGenero(String genero);
}
