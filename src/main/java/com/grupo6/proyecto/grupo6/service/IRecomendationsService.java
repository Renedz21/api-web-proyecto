package com.grupo6.proyecto.grupo6.service;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRecomendationsService {

    public List<Recomendations> findAll();

    public Page<Recomendations> findAll(Pageable pageable);

    public Recomendations findById(Long id);

    public Recomendations save(Recomendations data);

    public void delete(Long id);

    public List<Recomendations> findAllByLevel(Long id);
}
