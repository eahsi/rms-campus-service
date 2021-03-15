package com.revature.rms.campus.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.revature.rms.core.metadata.*;

import javax.persistence.*;
import java.util.List;


/**
 * POJO for the building object. The annotations:
 * @Data handles the getter and setter methods for each field
 * @Document handles the mapping to the database
 * @ToString handles the HashCode and ToString
 * @AllArgsConstructor handles the all arguments constructor
 * @NoArgsConstructor handles the no arguments constructor
 * @Id marks the selected field as a primary key
 * @NotNull ensures the field will cannot be null, all values except for abbrName are not null
 * @NotEmpty ensures the field will not be empty, buildings and corporateEmployees are ArrayLists so we dont' want these
 * fields to be empty either
 *
 * Lastly, we have a constructor containing every field except for the id.
 */

@Entity
@Data
public class Building extends Resource{

    @Column(nullable=false,unique=true) //covert h2
    private String name;

    @Column(nullable=false) //covert h2
    private String abbrName;

    @OneToOne
    @JoinColumn(nullable=false) //covert h2
    private Address physicalAddress;

    @Column(nullable=false) //covert h2
    private int trainingLead;

    @OneToMany(mappedBy = "building")
    private List<Amenity> amenities;

    @OneToMany(mappedBy = "building")
    private List<Room> rooms;

    @ManyToOne
    @JsonIgnore
    private Campus campus;


    public Building() {
    }

    public Building(int id, String name, String abbrName, Address physicalAddress, int trainingLead, List<Amenity> amenities, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
        this.amenities = amenities;
        this.rooms = rooms;
    }

    public Building(int id, String name, String abbrName, Address physicalAddress, int trainingLead) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
    }

    public Building(String name, String abbrName, Address physicalAddress, int trainingLead, List<Amenity> amenities, Campus campus) {
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
        this.amenities = amenities;
        this.campus = campus;
    }

    public Building(String name, String abbrName, Address physicalAddress, int trainingLead, List<Amenity> amenities, Campus campus, ResourceMetadata resourceMetadata) {
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
        this.amenities = amenities;
        this.campus = campus;
        this.resourceMetadata = resourceMetadata;
    }

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }

    @Override
    public String toString() {
        return "Building{" +
                "name='" + name + '\'' +
                ", abbrName='" + abbrName + '\'' +
                ", physicalAddress=" + physicalAddress +
                ", trainingLead=" + trainingLead +
                '}';
    }
}
