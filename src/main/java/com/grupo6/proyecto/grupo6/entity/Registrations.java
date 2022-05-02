package com.grupo6.proyecto.grupo6.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "registrations")
@Table(name = "registrations")
public class Registrations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String word;

    private Long recomendationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getRecomendationId() {
        return recomendationId;
    }

    public void setRecomendationId(Long recomendationId) {
        this.recomendationId = recomendationId;
    }
}
