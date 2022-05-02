package com.grupo6.proyecto.grupo6.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "data")
@Table(name = "data")
public class Data implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long dataLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(Long dataLevel) {
        this.dataLevel = dataLevel;
    }
}
