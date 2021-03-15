package com.revature.rms.campus.controllers;

import com.revature.rms.campus.entities.Room;
import com.revature.rms.campus.entities.RoomStatus;
import com.revature.rms.campus.entities.User;
import com.revature.rms.campus.services.RoomService;
import com.revature.rms.core.exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * This is for verifying the methods in RoomController.java function
 * as intended.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RoomControllerTest {

    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomService roomService;

    List<Room> testRooms;
    List<User> testUsers;

    @Before
    public void setup() {
        //Rooms
        Room r1 = new Room(1, "9000", 25,  new ArrayList<RoomStatus>(5),9000, new ArrayList<Integer>(3));
        Room r2 = new Room(2, "401", 30,  new ArrayList<RoomStatus>(5),123, new ArrayList<Integer>(2));
        Room r3 = new Room(3, "409", 1,  new ArrayList<RoomStatus>(5),9, new ArrayList<Integer>(1));
        testRooms = new ArrayList<>();
        testRooms.add(r1);
        testRooms.add(r2);
        testRooms.add(r3);

        //Users
        User u1 = new User(1, "Son", "Goku");
        User u2 = new User(9000, "Vegeta", "Unknown");
        User u3 = new User(3750, "Master", "Kai");
        testUsers = new ArrayList<>();
        testUsers.add(u1);
        testUsers.add(u2);
        testUsers.add(u3);

//        //Exception Handling
//        iie = new InvalidRequestException("This is for testing...");
//        rnfe = new ResourceNotFoundException("This is also for testing...");
    }

    @After
    public void tearDown() {
        testRooms = null;
    }

    /**
     * Tests that a new Room can be saved to the database.
     */
    @Test
    public void testSaveRoom() {
        when(roomService.save(testRooms.get(0))).thenReturn(testRooms.get(0));
        assertEquals(testRooms.get(0), roomController.saveRoom(testRooms.get(0)));
    }

    /**
     * Tests that a ResourceNotFoundException will be thrown if
     * a Room with an ID of 0 or less is passed.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testSaveRoomFailed() {
        Room nullRoom = null;
        roomController.saveRoom(nullRoom);
    }

    /**
     * Tests that a user can retrieve all Rooms
     */
    @Test
    public void testGetAllRooms() {
        when(roomService.findAll()).thenReturn(testRooms);
        assertEquals(testRooms, roomController.getAllRooms());
    }

    /**
     * Tests that a user can retrieve a Room by its ID.
     */
    @Test
    public void testGetRoomById() {
        when(roomService.findById(testRooms.get(0).getId())).thenReturn(Optional.ofNullable(testRooms.get(0)));
        assertEquals(testRooms.get(0), roomController.getRoomById(testRooms.get(0).getId()));
    }

    /**
     * Tests that an InvalidRequestException is thrown if an ID of
     * 0 or less is passed.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetRoomByIdFailed() {
        roomController.getRoomById(0);
    }

    /**
     * Tests that a ResourceNotFoundException is thrown if the ID passed
     * does not match a Room ID in the database.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testGetRoomByIdNotFound() {
        when(roomController.getRoomById(30)).thenThrow(ResourceNotFoundException.class);
    }

    /**
     * Tests that a Room can be retrieved by its owner ID.
     */
    @Test
    public void testGetRoomByOwnerId() {
        when(roomService.findByResourceOwner(testUsers.get(0).getId())).thenReturn(Collections.singletonList(testRooms.get(0)));
        assertEquals(Collections.singletonList(testRooms.get(0)), roomController.getRoomByOwnerId(testUsers.get(0).getId()));
    }

    /**
     * Tests that a Room can have it's details updated.
     */
    @Test
    public void testUpdateRoom() {
        Room r4 = new Room(4, "418", 30,  new ArrayList<RoomStatus>(5),123, new ArrayList<Integer>(2));
        when(roomService.update(testRooms.get(1))).thenReturn(r4);
        assertEquals(r4, roomController.updateRoom(testRooms.get(1)));
    }

    @Test
    public void testUpdateRoomNumber() {
        Room r4 = new Room(4, "418", 30,  new ArrayList<RoomStatus>(5),123, new ArrayList<Integer>(2));
        when(roomService.updateRoomNumber(testRooms.get(1))).thenReturn(r4);
        assertEquals(r4, roomController.updateRoomNumber(testRooms.get(1)));
    }

    /**
     * Tests that a Room can be deleted by its ID.
     */
    @Test
    public void testDeleteRoomById() {
        when(roomService.delete(testRooms.get(2).getId())).thenReturn(testRooms.get(2));
        assertTrue(roomController.deleteRoomById(testRooms.get(2).getId()));
    }

    /**
     * Tests that an InvalidRequestException is thrown if an ID of 0
     * or less is passed.
     */
    @Test(expected = InvalidRequestException.class)
    public void testDeleteRoomByIdFailed() {
        roomController.deleteRoomById(0);
    }

}
