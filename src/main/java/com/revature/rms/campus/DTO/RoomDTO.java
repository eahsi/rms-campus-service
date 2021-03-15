package com.revature.rms.campus.DTO;

import com.revature.rms.campus.entities.Room;
import com.revature.rms.campus.entities.RoomStatus;
import lombok.Data;
import com.revature.rms.core.metadata.*;

import java.util.List;

@Data
public class RoomDTO {

    private int id;

    private String roomNumber;

    private int maxOccupancy;

    private boolean active;

    private List<RoomStatus> roomStatus;

    private int batchId;

    private List<Integer> workOrders;

    private ResourceMetadata resourceMetadata;

    private int building_id;

    public RoomDTO() {
    }

    public RoomDTO(int id, String roomNumber, int maxOccupancy, boolean active, List<RoomStatus> roomStatus, int batchId, List<Integer> workOrders, ResourceMetadata resourceMetadata) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.maxOccupancy = maxOccupancy;
        this.active = active;
        this.roomStatus = roomStatus;
        this.batchId = batchId;
        this.workOrders = workOrders;
        this.resourceMetadata = resourceMetadata;
    }

    public RoomDTO(int id, String roomNumber, int maxOccupancy, boolean active, List<RoomStatus> roomStatus, int batchId, List<Integer> workOrders, ResourceMetadata resourceMetadata, int building_id) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.maxOccupancy = maxOccupancy;
        this.active = active;
        this.roomStatus = roomStatus;
        this.batchId = batchId;
        this.workOrders = workOrders;
        this.resourceMetadata = resourceMetadata;
        this.building_id = building_id;
    }

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.maxOccupancy = room.getMaxOccupancy();
        this.roomStatus = room.getCurrentStatus();
        this.batchId = room.getBatchId();
        this.workOrders = room.getWorkOrders();
        this.resourceMetadata = room.getResourceMetadata();
        this.building_id = room.getBuilding().getId();
    }
}
