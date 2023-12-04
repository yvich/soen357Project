package com.tar.iq.petcare.model;

import java.io.Serializable;

public class User implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    String email;
    String time;
    String date;
    String phone;
    String clinic;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;
    public void setEmail(String email) {
        this.email = email;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String profileImageUrl;



    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    String rate;
    String id;
    String breed;
    String dob;
    String allergies;
    String type;
    String gender;

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSterlization() {
        return sterlization;
    }

    public void setSterlization(String sterlization) {
        this.sterlization = sterlization;
    }

    public String getVet() {
        return vet;
    }

    public void setVet(String vet) {
        this.vet = vet;
    }

    String sterlization;
    String vet;


    public User() {
    }


    public User(String email, String name,String url) {
        this.email = email;
        this.profileImageUrl = url;
        this.name = name;
    }
    public User(String name,String address, String clinic, String phone,String id) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.clinic = clinic;
        this.id = id;
    }
    public User(String name,String breed, String dob, String allergies,String gender,String type,String sterlization,String vet,String id) {
        this.name = name;
        this.breed = breed;
        this.dob = dob;
        this.allergies = allergies;
        this.gender = gender;
        this.sterlization = sterlization;
        this.vet = vet;
        this.type = type;
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
