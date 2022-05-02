package com.grupo6.proyecto.grupo6.implement;

import com.grupo6.proyecto.grupo6.dao.IDataDao;
import com.grupo6.proyecto.grupo6.entity.Data;
import com.grupo6.proyecto.grupo6.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IDataServiceImpl implements IDataService {

    @Autowired
    private IDataDao dataDao;

    @Override
    public List<Data> findAll() {
        return dataDao.findAll();
    }

    @Override
    public List<Data> resultData(String data) {
        return dataDao.resultData(data);
    }

    @Override
    public Page<Data> findAll(Pageable pageable) {
        return dataDao.findAll(pageable);
    }

    @Override
    public Data findById(Long id) {
        return dataDao.findById(id).orElse(null);
    }

    @Override
    public Data save(Data data) {
        return dataDao.save(data);
    }

    @Override
    public void delete(Long id) {
        dataDao.deleteById(id);
    }
}
