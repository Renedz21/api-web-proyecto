package com.grupo6.proyecto.grupo6.pojo;

import org.springframework.beans.factory.annotation.Value;

public class CantRepWord {
    private String word;
    private Long count;

    public CantRepWord(String word, Long count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
