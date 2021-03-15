package com.revature.rms.campus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.revature.rms.core.metadata.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Room extends Resource{

    @Column(nullable=false)
    private String roomNumber;
    
    @Column(nullable=false)
    private int maxOccupancy;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    private List<RoomStatus> currentStatus;

    @Column(nullable=false)
    private int batchId;

    @ElementCollection
    private List<Integer> workOrders;

    @ManyToOne
    @JsonIgnore
    private Building building;

    public Room() {
    }

    public Room(int id, String roomNumber, Integer maxOccupancy, ArrayList<RoomStatus> currentStatus, int batchId, ArrayList<Integer> workOrders) {
        this(id, roomNumber, maxOccupancy, batchId);
        this.currentStatus = currentStatus;
        this.workOrders = workOrders;
    }

    public Room(int id, String roomNumber, Integer maxOccupancy, int batchId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.maxOccupancy = maxOccupancy;
        this.batchId = batchId;
    }

    public Room(int id, String roomNumber, Integer maxOccupancy, int batchId, ResourceMetadata resourceMetadata) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.maxOccupancy = maxOccupancy;
        this.batchId = batchId;
        this.resourceMetadata = resourceMetadata;
    }

    public void addRoomStatus(RoomStatus status) {
        currentStatus.add(status);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public List<RoomStatus> getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(List<RoomStatus> currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public List<Integer> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<Integer> workOrders) {
        this.workOrders = workOrders;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", maxOccupancy=" + maxOccupancy +
                ", currentStatus=" + currentStatus +
                ", batchId=" + batchId +
                '}';
    }
}
