package com.grupo6.proyecto.grupo6.dao;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.entity.Registrations;
import com.grupo6.proyecto.grupo6.pojo.CantRepWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IRegistrationDao extends JpaRepository<Registrations, Long> {

    @Query(value="SELECT r,c,u FROM registrations r " +
            "inner join users u " +
            "on r.userId = u.id " +
            "inner join recomendations c " +
            "on r.recomendationId = c.level ")
    Page<Registrations> findAllByFilter(Pageable pageable);

    @Query(value = "SELECT count(r) from registrations r")
    Integer cantidadRegister();

    @Query(value = "SELECT new com.grupo6.proyecto.grupo6.pojo.CantRepWord(r.word,count(r.word)) from registrations r " +
            "group by r.word " +
            "order by count(r.word) desc ")
    List<CantRepWord> cantRep();

    List<Registrations> findByUserId(Long id);
}
