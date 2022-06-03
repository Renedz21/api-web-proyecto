package com.grupo6.proyecto.grupo6.dao;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IRecomendationsDao extends JpaRepository<Recomendations, Long> {

    List<Recomendations> findAllByLevel(Long level);
}
