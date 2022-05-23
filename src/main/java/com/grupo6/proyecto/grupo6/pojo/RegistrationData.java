package com.grupo6.proyecto.grupo6.pojo;

public class RegistrationData {
    //datos importartes de la tabla Registration
    private String word;
    private Long recomendationId;
    //datos importantes de la tabla User
    private String firstName;
    private String lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
