package com.grupo6.proyecto.grupo6.dao;

import com.grupo6.proyecto.grupo6.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDataDao extends JpaRepository<Data, Long> {

    @Query(value="SELECT d FROM data d " +
            "where 1=1 " +
            "and (:data is null or d.name like :data)")
    List<Data> resultData(@Param("data") String data);

}
