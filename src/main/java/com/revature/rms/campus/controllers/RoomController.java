package com.revature.rms.campus.controllers;

import com.revature.rms.campus.entities.Room;
import com.revature.rms.core.exceptions.*;
import com.revature.rms.campus.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campuses/rooms") // service name/controller name
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * saveRoom method: Takes in a room object as the input.
     *
     * @param room newly persisted room object
     * @return the newly added room object
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room saveRoom(@RequestBody Room room) {

        if(room == null){

            throw new ResourceNotFoundException("Room cannot be null!");

        }

        return roomService.save(room);

    }

    /**
     * getAllRooms method: Returns a list of all the room objects in the database.
     *
     * @return a list of all the rooms
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getAllRooms() { return roomService.findAll(); }


    /**
     * getRoomById method: Returns a room object when the id int matches a record in the database.
     *
     * @param id roomId int value
     * @return a room with matching id
     */
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Room getRoomById(@PathVariable int id) {

        if(id <= 0){

            throw new InvalidRequestException("ID cannot be less than or equal to zero!");

        }

        Optional<Room> _room = roomService.findById(id);

        if(!_room.isPresent()){

            throw new ResourceNotFoundException("No room found!");

        }

        return roomService.findById(id).get();

    }

    /**
     * getRoomByOwner method: gets a list of rooms owned by a person
     *
     * @param id ID of the owner
     * @return List of rooms
     */

    @GetMapping(value = "/owners/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getRoomByOwnerId(@PathVariable int id){
        return roomService.findByResourceOwner(id);
    }


    /**
     * updateRoom method: The room object is inputted and changes are saved.
     *
     * @param room newly updated room object
     * @return updated/modified room object
     */

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room updateRoom(@RequestBody Room room) { return roomService.update(room); }

    @PutMapping(value = "/room-number", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room updateRoomNumber(@RequestBody Room room) { return roomService.updateRoomNumber(room); }

    /**
     * deleteRoomById method: The room object is deleted based on its roomId int
     *
     * @param id roomId int value
     */
    @DeleteMapping(value = "/id/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteRoomById(@PathVariable int id) {

        if(id <= 0){

            throw new InvalidRequestException("ID cannot be less than or equal to zero!");

        }

        roomService.delete(id);
        return true;
    }

}
