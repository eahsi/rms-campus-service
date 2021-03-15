package com.revature.rms.campus.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.rms.campus.entities.*;
import com.revature.rms.core.metadata.*;

import javax.persistence.*;
import java.util.List;

public class BuildingDTO {

    private int id;

    private String name;

    private String abbrName;

    private Address physicalAddress;

    private int trainingLead;

    private List<Amenity> amenities;

    private List<Room> rooms;

    private ResourceMetadata resourceMetadata;

    private int campusId;

    public BuildingDTO() {
    }

    public BuildingDTO(String name, String abbrName, Address physicalAddress, int trainingLead, List<Amenity> amenities, List<Room> rooms, ResourceMetadata resourceMetadata, int campusId) {
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
        this.amenities = amenities;
        this.rooms = rooms;
        this.resourceMetadata = resourceMetadata;
        this.campusId = campusId;
    }

    public BuildingDTO(int id, String name, String abbrName, Address physicalAddress, int trainingLead, List<Amenity> amenities, List<Room> rooms, ResourceMetadata resourceMetadata, int campusId) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
        this.amenities = amenities;
        this.rooms = rooms;
        this.resourceMetadata = resourceMetadata;
        this.campusId = campusId;
    }

    public BuildingDTO(int id, String name, String abbrName, Address physicalAddress, int trainingLead, List<Amenity> amenities, ResourceMetadata resourceMetadata, int campusId) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.physicalAddress = physicalAddress;
        this.trainingLead = trainingLead;
        this.amenities = amenities;
        this.resourceMetadata = resourceMetadata;
        this.campusId = campusId;
    }
}
