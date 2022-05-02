package com.grupo6.proyecto.grupo6.service;

import com.grupo6.proyecto.grupo6.entity.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDataService {

    public List<Data> findAll();

    public List<Data> resultData(String data);

    public Page<Data> findAll(Pageable pageable);

    public Data findById(Long id);

    public Data save(Data data);

    public void delete(Long id);
}
