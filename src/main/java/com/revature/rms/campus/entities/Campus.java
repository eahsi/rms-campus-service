package com.revature.rms.campus.entities;

import lombok.Data;

import com.revature.rms.core.metadata.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO for the campus object. The annotations:
 * @Data handles the getter and setter methods for each field
 * @Id marks the selected field as a primary key
 *
 * Lastly, we have a constructor containing every field except for the id.
 */
@Data
@Entity
public class Campus extends Resource{

    @Column(nullable=false,unique=true)
    private String name;

    @Column(nullable=false)
    private String abbrName;

    @OneToOne
    @JoinColumn(nullable=false)
    private Address shippingAddress;

    @Column(nullable=false)
    private int trainingManagerId;

    @Column(nullable=false)
    private int stagingManagerId;

    @Column(nullable=false)
    private int hrLead;

    @OneToMany(mappedBy = "campus")
    private List<Building> buildings;

    @ElementCollection
    private List<Integer> corporateEmployees;

    public Campus() {
    }

    public Campus(int id, String name, String abbrName, Address shippingAddress, int trainingManagerId, int stagingManagerId, int hrLead, ArrayList<Building> buildings, ArrayList<Integer> corporateEmployees) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.shippingAddress = shippingAddress;
        this.trainingManagerId = trainingManagerId;
        this.stagingManagerId = stagingManagerId;
        this.hrLead = hrLead;
        this.buildings = buildings;
        this.corporateEmployees = corporateEmployees;
    }

    public Campus(int id, String name, String abbrName, Address shippingAddress, int trainingManagerId, int stagingManagerId, int hrLead, List<Integer> corporateEmployees) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.shippingAddress = shippingAddress;
        this.trainingManagerId = trainingManagerId;
        this.stagingManagerId = stagingManagerId;
        this.hrLead = hrLead;
        this.corporateEmployees = corporateEmployees;
    }

    public Campus(int id, String name, String abbrName, Address shippingAddress, int trainingManagerId, int stagingManagerId, int hrLead, List<Integer> corporateEmployees, ResourceMetadata resourceMetadata) {
        this.id = id;
        this.name = name;
        this.abbrName = abbrName;
        this.shippingAddress = shippingAddress;
        this.trainingManagerId = trainingManagerId;
        this.stagingManagerId = stagingManagerId;
        this.hrLead = hrLead;
        this.corporateEmployees = corporateEmployees;
        this.resourceMetadata = resourceMetadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "Campus{" +
                "name='" + name + '\'' +
                ", abbrName='" + abbrName + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", trainingManagerId=" + trainingManagerId +
                ", stagingManagerId=" + stagingManagerId +
                ", hrLead=" + hrLead +
                ", corporateEmployees=" + corporateEmployees +
                '}';
    }
}
