package com.grupo6.proyecto.grupo6.service;

import com.grupo6.proyecto.grupo6.entity.Registrations;
import com.grupo6.proyecto.grupo6.pojo.CantRepWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRegistrationsService {

    public List<Registrations> findAll();

    public Page<Registrations> findAll(Pageable pageable);

    public Registrations findById(Long id);

    public Registrations save(Registrations data);

    public void delete(Long id);

    public Integer catRegistrations();

    public List<CantRepWord> cantRep();

    public List<Registrations> findByUserId(Long id);
}
