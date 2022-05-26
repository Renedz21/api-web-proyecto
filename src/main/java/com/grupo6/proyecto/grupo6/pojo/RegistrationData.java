package com.grupo6.proyecto.grupo6.pojo;

public class RegistrationData {
    //datos importartes de la tabla Registration
    private String word;
    private Long recomendationId;
    //datos importantes de la tabla User
    private String Name;
    private Long age;
    private Long phone;
    private String workArea;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }
}
