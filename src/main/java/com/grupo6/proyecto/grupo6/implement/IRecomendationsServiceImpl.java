package com.grupo6.proyecto.grupo6.implement;

import com.grupo6.proyecto.grupo6.dao.IRecomendationsDao;
import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.service.IRecomendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRecomendationsServiceImpl implements IRecomendationsService {

    @Autowired
    private IRecomendationsDao recomendationsDao;


    @Override
    public List<Recomendations> findAll() {
        return recomendationsDao.findAll();
    }

    @Override
    public Page<Recomendations> findAll(Pageable pageable) {
        return recomendationsDao.findAll(pageable);
    }

    @Override
    public Recomendations findById(Long id) {
        return recomendationsDao.findById(id).orElse(null);
    }

    @Override
    public Recomendations save(Recomendations data) {
        return recomendationsDao.save(data);
    }

    @Override
    public void delete(Long id) {
        recomendationsDao.deleteById(id);
    }

    @Override
    public Recomendations findAllByLevel(Long id) {
        return recomendationsDao.findAllByLevel(id);
    }
}
