package com.grupo6.proyecto.grupo6.implement;

import com.grupo6.proyecto.grupo6.dao.IRegistrationDao;
import com.grupo6.proyecto.grupo6.entity.Registrations;
import com.grupo6.proyecto.grupo6.service.IRegistrationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRegistrationsServiceImpl implements IRegistrationsService {

    @Autowired
    private IRegistrationDao registrationDao;

    @Override
    public List<Registrations> findAll() {
        return registrationDao.findAll();
    }

    @Override
    public Page<Registrations> findAll(Pageable pageable) {
        return registrationDao.findAllByFilter(pageable);
    }

    @Override
    public Registrations findById(Long id) {
        return registrationDao.findById(id).orElse(null);
    }

    @Override
    public Registrations save(Registrations data) {
        return registrationDao.save(data);
    }

    @Override
    public void delete(Long id) {
        registrationDao.deleteById(id);
    }
}
