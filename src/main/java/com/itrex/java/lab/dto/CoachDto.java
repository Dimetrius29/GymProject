package com.itrex.java.lab.dto;

import java.util.Objects;

public class CoachDto {

    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String specialization;
    private Double priceOfActivity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Double getPriceOfActivity() {
        return priceOfActivity;
    }

    public void setPriceOfActivity(Double priceOfActivity) {
        this.priceOfActivity = priceOfActivity;
    }

    @Override
    public String toString() {
        return "\nCoachDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", specialization='" + specialization + '\'' +
                ", priceOfActivity=" + priceOfActivity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachDto coachDTO = (CoachDto) o;
        return id.equals(coachDTO.id) && name.equals(coachDTO.name) && surname.equals(coachDTO.surname) && phone.equals(coachDTO.phone) && specialization.equals(coachDTO.specialization) && priceOfActivity.equals(coachDTO.priceOfActivity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phone, specialization, priceOfActivity);
    }
}
