package com.revature.rms.campus.services;

import com.revature.rms.campus.entities.Room;
import com.revature.rms.campus.entities.RoomStatus;
import com.revature.rms.core.metadata.*;
import com.revature.rms.core.exceptions.*;
import com.revature.rms.campus.repositories.RoomRepository;
import com.revature.rms.campus.repositories.RoomStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The methods in this service call to methods from the roomRepository, roomStatusRepository, metadataService and metadataRepository in order to give the basic CRUD features to
 * the application. The methods in this service are custom as a result of TDD. For more information about the testing
 * see RoomServiceTests.
 */
@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    /**
     * Save Method: Takes in a room object as the input. It is then saved or persisted
     * to the database.
     *
     * @param room
     * @return The new saved room object
     */
    @Transactional
    public Room save(Room room){
        if(room == null){
            throw new ResourceNotFoundException("Room cannot be null!");
        }
        Room persisted = roomRepository.save(room);
        for (RoomStatus status: room.getCurrentStatus()) {
            status.setRoom(persisted);
            saveStatus(status);
        }
        return persisted;
    }

    /**
     * findAll method: returns a list of all the room objects in the database.
     *
     * @return a list of all the rooms
     */
    @Transactional(readOnly = true)
    public List<Room> findAll(){
            Iterable<Room> r = roomRepository.findAll();
            List<Room> list = getListFromIterator(r);
            return list;
    }

    /**
     * findById Method: This takes in the room id parameter. The room object with
     * the id equal to the given id is returned.
     *
     * @param id
     * @return The specific room with the given id
     */
    @Transactional(readOnly = true)
    public Optional<Room> findById(int id){
        if (id <= 0) {
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }

        Optional<Room> _room = roomRepository.findById(id);
        if(!_room.isPresent()){
            throw new ResourceNotFoundException("No room found with that ID!");
        }
        return _room;
    }

    /**
     * findByRoomNumber Method: This takes in the room number as the input
     * parameter. The room object that has the specified
     * room number is returned.
     *
     * @param roomNum
     * @return the room object with the same room number as the input parameter.
     */
    @Transactional(readOnly = true)
    public Optional<Room> findByRoomNumber(String roomNum){
        if (roomNum.isEmpty() || (Integer.parseInt(roomNum) <= 0)) {
            throw new InvalidRequestException("Room number cannot be less than or equal to zero!");
        }

        Optional<Room> _room = roomRepository.findByRoomNumber(roomNum);

        if(!_room.isPresent()) {
            throw new ResourceNotFoundException("Room with that room number does not exist!");
        }
        return _room;
    }

    /**
     * findByMaxOccupancy Method: This takes in the required or specified
     * occupancy that we are searching for and returns a list of all the
     * rooms that have that same occupancy.
     *
     * @param occupancy
     * @return a list of all the rooms with the specified occupancy.
     */
    @Transactional(readOnly = true)
    public List<Room> findByMaxOccupancy(int occupancy){

        return roomRepository.findByMaxOccupancy(occupancy);
    }

    /**
     * findByResourceOwner: Takes the ID of an appuser, and finds a list of rooms they own.
     *
     * @param id ID of the owner
     * @return List of rooms
     */
    @Transactional
    public List<Room> findByResourceOwner(Integer id){
        if(id <= 0){
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }
        Iterable<Room> allRooms = roomRepository.findAll();
        List<Room> rooms = new ArrayList<Room>();
        for(Room room : allRooms){
            ResourceMetadata data = room.getResourceMetadata();
            if(data.getResourceOwner() == id){
                rooms.add(room);
            }
        }
        if(rooms.isEmpty()){
            throw new ResourceNotFoundException("No room found by that resource owner!");
        }
        return rooms;
    }

    /**
     * Update Method: The room object is inputted and changes are saved.
     * The modified object is returned.
     *
     * @param room
     * @return Updated/Modified room object
     */
    @Transactional
    public Room update(Room room){
        Room oldRoom;
        oldRoom = roomRepository.findById(room.getId()).get();


        room.setBuilding(oldRoom.getBuilding());
        room.setCurrentStatus(oldRoom.getCurrentStatus()); // TODO append list of RoomStatuses by updated room's roomstatus
        room.setWorkOrders(oldRoom.getWorkOrders());
        room.setResourceMetadata(oldRoom.getResourceMetadata());
        room.setBatchId(oldRoom.getBatchId());

        room.getResourceMetadata().setLastModifiedDateTime(LocalDateTime.now().toString());
        room.getResourceMetadata().setResourceCreator(oldRoom.getResourceMetadata().getResourceCreator());
        room.getResourceMetadata().setResourceCreationDateTime(oldRoom.getResourceMetadata().getResourceCreationDateTime());
        room.getResourceMetadata().setLastModifier(oldRoom.getResourceMetadata().getLastModifier());
        room.getResourceMetadata().setResourceOwner(oldRoom.getResourceMetadata().getResourceOwner());
        room.getResourceMetadata().setCurrentlyActive(oldRoom.getResourceMetadata().isCurrentlyActive());

        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoomNumber(Room room) {

        Room oldRoom;
        oldRoom = roomRepository.findById(room.getId()).get();

        room.setMaxOccupancy(oldRoom.getMaxOccupancy());
        room.setCurrentStatus(oldRoom.getCurrentStatus()); // TODO append list of RoomStatuses by updated room's roomstatus
        room.setBatchId(oldRoom.getBatchId());
        room.setWorkOrders(oldRoom.getWorkOrders());
        room.setBuilding(oldRoom.getBuilding());

        room.setResourceMetadata(oldRoom.getResourceMetadata());
        ResourceMetadata resourceMetadata = oldRoom.getResourceMetadata();

        room.setResourceMetadata(resourceMetadata);
        room.getResourceMetadata().setLastModifiedDateTime(LocalDateTime.now().toString());
        // this should change every time an update is made

        return roomRepository.save(room);

    }

    /**
     * Soft Delete Method: Updates the room object by setting active to
     * false (to indicate the room is no longer in use). Soft delete is
     * implemented to achieve data in the event of auditing. Soft delete
     * may need some modifications to pass all tests.
     *
     * The method takes in the room id. The input is tested to ensure
     * that it is not empty, negative or zero. if the input is empty,
     * negative or zero, an InvalidRequestException is thrown.
     *
     * The room id is used to retrieve the specific room object. The
     * active parameter of the retrieved room object is set to false
     * and the room object is then saved or updated.
     * @param id
     * @return The Updated room objected.
     */
    @Transactional
    public Room delete(int id){
        if (id <= 0) {
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }
        Room deactivateRoom = roomRepository.findById(id).get();
        return update(deactivateRoom);
    }

    // +--------Methods using RoomStatusRepository------------------------------------+
    /**
     * findAllStatusBySubmitter Method: The submitter id is inputted as
     * the search criteria. A list of room status objects that have the
     * same submitter id as the given parameter will be returned.
     *
     * @param id
     * @return the list of room status objects submitted by the given user id.
     */
    @Transactional(readOnly = true)
    public List<RoomStatus> findAllStatusBySubmitter(int id){
        return roomStatusRepository.findAllBySubmitterId(id);
    }

    /**
     * findAllStatusByDate Method: A date of type String is passed into the method.
     * The given date is then used to find all room status objects that have the same
     * date as there submitted date parameter.
     *
     * @param date
     * @return the list of room status objects with the specified submitted date
     */
    @Transactional(readOnly = true)
    public List<RoomStatus> findAllStatusByDate(String date){ return roomStatusRepository.findAllBySubmittedDateTime(date);}

    /**
     * findByStatusId Method: This takes in the status id parameter. The status id
     * is unique in the system, that is no two room statuses can have the same id.
     * Next, the room status object is found using the status id, that is the room
     * status that have the same status id is returned.
     *
     * @param id
     * @return the room status with the given status id.
     */
    @Transactional(readOnly = true)
    public Optional<RoomStatus> findStatusById(int id){
        return roomStatusRepository.findById(id);
    }

    /**
     * findAll Method: This method returns a list of all the room status objects
     * in the database.
     *
     * @return a list of all the room status objects
     */
    @Transactional(readOnly = true)
    public List<RoomStatus> findAllStatus(){
        Iterable<RoomStatus> r = roomStatusRepository.findAll();
        List<RoomStatus> list = getListFromIterator(r);
        return list;
    }

    /**
     * saveStatus Method: This method takes in a new room status object and
     * saves it to the database.
     *
     * @param roomStatus
     */
    @Transactional
    public void saveStatus(RoomStatus roomStatus){
        roomStatusRepository.save(roomStatus);
    }

    /**
     * Update Method: The room status object is inputted and changes are saved.
     * The modified object is returned.
     *
     * @param roomStatus
     * @return Updated/Modified room status object
     */
    @Transactional
    public RoomStatus updateStatus(RoomStatus roomStatus){
        return roomStatusRepository.save(roomStatus);
    }

    /**
     * getListFromIterator Method: Is a custom method that iterates and adds each object to a list of the specified Generic.
     *
     * @param iterable an Iterable that wants to be converted into an ArrayList
     * @param <T> Generic of any ObjectType
     * @return Returns a List of type T
     */
    public static <T> List<T> getListFromIterator(Iterable<T> iterable)
    {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
