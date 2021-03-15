package com.revature.rms.campus.entities;

//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
import lombok.Data;

//import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
//@Document
//commentted out bc mangodb
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Data
public class Address {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String unitStreet;

    @Column(nullable=false)
    private String city;

    @Column(nullable=false)
    private String state;

    @Column(nullable=false)
    private String zip;

    @Column(nullable=false)
    private String country;


    public Address() {
    }

    public Address(int id, String unitStreet, String city, String state, String zip, String country) {
        this.id = id;
        this.unitStreet = unitStreet;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
    public Address(String unitStreet, String city, String state, String zip, String country) {
        this.unitStreet = unitStreet;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "unitStreet='" + unitStreet + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
