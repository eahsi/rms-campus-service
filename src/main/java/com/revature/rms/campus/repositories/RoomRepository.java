package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.Room;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer>{

    /**
     * findByRoomNumber Method: This takes in the room number as the input
     * parameter. The input room number is validated to ensure that it is not
     * empty or negative or zero. If the room number fails this validation an
     * InvalidInputException is thrown.
     * The room object with the specified room number is retrieved. The room
     * object is tested to ensure that the room object is present. If the room
     * object is not present then a ResourceNotFoundException is thrown.
     * If all these validations are passed, the room object that have the specified
     * room number is returned.
     * @param name
     * @return the room object with the same room number as the input parameter.
     */
    Optional<Room> findByRoomNumber(String name);

    /**
     * findByMaxOccupancy Method: This takes in the required or specified
     * occupancy that we are searching for and returns a list of all the
     * rooms that have that same occupancy.
     * @param occupancy
     * @return a list of all the rooms with the specified occupancy.
     */
    List<Room> findByMaxOccupancy(int occupancy);
}