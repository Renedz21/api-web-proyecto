package com.grupo6.proyecto.grupo6.dao;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IRecomendationsDao extends JpaRepository<Recomendations, Long> {

    Recomendations findAllByLevel(Long level);
}
