package com.grupo6.proyecto.grupo6.dao;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.entity.Registrations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRegistrationDao extends JpaRepository<Registrations, Long> {

    @Query(value="SELECT r,c,u FROM registrations r " +
            "inner join users u " +
            "on r.userId = u.id " +
            "inner join recomendations c " +
            "on r.recomendationId = c.id " +
            "and u.type='register' ")
    Page<Registrations> findAllByFilter(Pageable pageable, @Param("dato") String dato);
}
