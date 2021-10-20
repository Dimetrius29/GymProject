package com.itrex.java.lab.entity;

public class Coach {

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
        return "Coach{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", specialization='" + specialization + '\'' +
                ", price_of_activity=" + priceOfActivity +
                '}';
    }
}