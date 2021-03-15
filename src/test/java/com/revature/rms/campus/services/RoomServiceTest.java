package com.revature.rms.campus.services;

import com.revature.rms.campus.entities.Room;
import com.revature.rms.campus.entities.RoomStatus;
import com.revature.rms.campus.repositories.RoomRepository;
import com.revature.rms.campus.repositories.RoomStatusRepository;
import com.revature.rms.core.metadata.*;
import com.revature.rms.core.exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests all the service methods in RoomService.class These methods are
 * for the manipulation or retrieval of all fields related with Rooms, including
 * metadata, buildings, work-orders, and statuses.
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class) //Can add .Silent.class if need to remove stub interruption
public class RoomServiceTest {

    @Mock
    RoomRepository repo;

    @Mock
    RoomStatusRepository roomStatusRepository;

    @InjectMocks
    @Autowired
    RoomService sut;

    //Test Data
    List<Room> testRooms;
    Room nullRoom;
    ArrayList<Integer> workorders;
    ArrayList<RoomStatus> testStatuses;
    ResourceMetadata resourceMetadata;

    @Before
    public void setup() {
        //Room statuses
        RoomStatus s1 = new RoomStatus(1, false, false, "Jan 28, 2020", 1, "And So It Begins...");
        RoomStatus s2 = new RoomStatus(2, true, false, "Jan 29, 2020", 1, "And Where it Is...");
        RoomStatus s3 = new RoomStatus(3, false, true, "Jan 30, 2020", 1, "And How It Ends...");
        testStatuses = new ArrayList<>();
        testStatuses.add(s1);
        testStatuses.add(s2);
        testStatuses.add(s3);

        //Work Orders
        workorders = new ArrayList<>();
        workorders.add(1);
        workorders.add(2);
        workorders.add(3);

        //Rooms
        Room r1 = new Room(1, "404", 1, testStatuses, 1, workorders);
        Room r2 = new Room(2, "606", 25, testStatuses, 2, workorders);
        Room r3 = new Room(3, "808", 300, testStatuses, 3, workorders);
        testRooms = new ArrayList<>();
        testRooms.add(r1);
        testRooms.add(r2);
        testRooms.add(r3);
    }

    @After
    public void tearDown() {
        testStatuses = null;
        workorders = null;
        testRooms = null;
        resourceMetadata = null;
    }

    /**
     * This tests that a new Room can be saved to the database.
     */
    @Test
    public void testSaveRoom() {
        when(repo.save(testRooms.get(0))).thenReturn(testRooms.get(0));
        assertEquals(testRooms.get(0), sut.save(testRooms.get(0)));
    }

    /**
     * This tests that a ResourceNotFoundException will be thrown if a null Room
     * tries to be entered into the database.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testSaveRoomNullRoom() {
        sut.save(nullRoom);
    }

    /**
     * This tests that all Rooms in the database can be retrieved.
     */
    @Test
    public void testGetAllRooms() {
        when(repo.findAll()).thenReturn(testRooms);
        assertEquals(testRooms, sut.findAll());
    }

    /**
     * This tests that a specific Room can be retrieved by
     * its Room Id
     */
    @Test
    public void testGetById() {
        when(repo.findById(testRooms.get(0).getId())).thenReturn(Optional.ofNullable(testRooms.get(0)));
        assertEquals(Optional.ofNullable(testRooms.get(0)), sut.findById(testRooms.get(0).getId()));
    }

    /**
     * This tests that an InvalidRequestException is thrown if an
     * invalid id is input.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetByIdFailed() {
        int id = 0;
        sut.findById(id);
    }

    /**
     * This tests that a ResourceNotFoundException is thrown if no room
     * is retrieved by the given Id.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdNotFound() {
        when(sut.findById(99999)).thenThrow(ResourceNotFoundException.class);
    }

    /**
     * This tests that a specific Room can be retrieved by its
     * room number value.
     */
    @Test
    public void testGetByRoomNumber() {
        when(repo.findByRoomNumber(testRooms.get(0).getRoomNumber())).thenReturn(Optional.ofNullable(testRooms.get(0)));
        assertEquals(Optional.ofNullable(testRooms.get(0)), sut.findByRoomNumber(testRooms.get(0).getRoomNumber()));
    }

    /**
     * This tests that an InvalidRequestException is thrown if an invalid
     * value is entered as the room number.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetByRoomNumberNoRoom() {
        when(sut.findByRoomNumber("")).thenThrow(InvalidRequestException.class);
    }

    /**
     * This tests that an InvalidRequestException is thrown if the number parsed
     * is invalid.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetByRoomNumberParseError() {
        when(sut.findByRoomNumber("0")).thenThrow(InvalidRequestException.class);
    }

    /**
     * This tests that a ResourceNotFoundException is thrown if no Room
     * is retrieved with the given room number.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testGetByRoomNumberNotFound() {
        when(sut.findByRoomNumber("999999")).thenThrow(ResourceNotFoundException.class);
    }

    /**
     * This tests that a Room can be retrieved by its max occupancy value.
     */
    @Test
    public void testGetByMaxOccupancy() {
        when(repo.findByMaxOccupancy(testRooms.get(0).getMaxOccupancy())).thenReturn(Collections.singletonList(testRooms.get(0)));
        assertEquals(Collections.singletonList(testRooms.get(0)), sut.findByMaxOccupancy(testRooms.get(0).getMaxOccupancy()));
    }

    /**
     * Tests that an InvalidRequestException is thrown if an
     * invalid id is entered as a value.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetByResourceOwnerFailed() {
        int id = 0;
        sut.findByResourceOwner(id);
    }

    /**
     * Tests that a ResourceNotFoundException is thrown if no resource belonging
     * to the owner id is found.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testGetByResourceOnwerNotFound() {
        when(sut.findByResourceOwner(100)).thenThrow(ResourceNotFoundException.class);
    }

    /**
     * Tests that a Room can be updated by finding it's old id, and saving the
     * new data over it.
     */
    @Ignore // sut.update() was refactored. Now this test needs to refactored.
    @Test
    public void testUpdateRoom() {
        when(repo.save(testRooms.get(1))).thenReturn(testRooms.get(1));
        when(repo.findById(testRooms.get(1).getId())).thenReturn(Optional.ofNullable(testRooms.get(1)));
        assertEquals(testRooms.get(1), sut.update(testRooms.get(1)));
    }

    /**
     * Tests that a room can be soft-deleted (deactivated) by it's given id.
     */
    @Ignore // when did this test get here???
    @Test
    public void testDeactivateRoom() {
        when(repo.save(testRooms.get(0))).thenReturn(testRooms.get(0));
        when(repo.findById(testRooms.get(0).getId())).thenReturn(Optional.ofNullable(testRooms.get(0)));
        assertEquals(testRooms.get(0), sut.delete(testRooms.get(0).getId()));
    }

    /**
     * Tests that an InvalidRequestException is thrown if a user tries to delete
     * a Room with an invalid id.
     */
    @Test(expected = InvalidRequestException.class)
    public void testDeactivateRoomFailed() {
        int id = 0;
        sut.delete(id);
    }

    /**
     * Tests that all RoomStatuses can be retrieved by the submitterId.
     */
    @Test
    public void testGetAllBySubmitterId() {
        when(roomStatusRepository.findAllBySubmitterId(1)).thenReturn(testStatuses);
        assertEquals(testStatuses, sut.findAllStatusBySubmitter(1));
    }

    /**
     * Tests that all RoomStatuses can be retrieved based on date.
     */
    @Test
    public void testGetAllStatusByDate() {
        when(roomStatusRepository.findAllBySubmittedDateTime(testStatuses.get(0).getSubmittedDateTime())).thenReturn(Collections.singletonList(testStatuses.get(0)));
        assertEquals(Collections.singletonList(testStatuses.get(0)), sut.findAllStatusByDate(testStatuses.get(0).getSubmittedDateTime()));
    }

    /**
     * Tests that a RoomStatus can be retrieved by its id.
     */
    @Test
    public void testGetStatusById() {
        when(roomStatusRepository.findById(testStatuses.get(0).getId())).thenReturn(Optional.ofNullable(testStatuses.get(0)));
        assertEquals(Optional.ofNullable(testStatuses.get(0)), sut.findStatusById(testStatuses.get(0).getId()));
    }

    /**
     * Tests that all RoomStatuses can be retrieved.
     */
    @Test
    public void testGetAllStatuses() {
        when(roomStatusRepository.findAll()).thenReturn(testStatuses);
        assertEquals(testStatuses, sut.findAllStatus());
    }

    /**
     * Tests that a new RoomStatus can be saved to the database.
     */
    @Test
    public void testSaveStatus() {
        when(roomStatusRepository.save(testStatuses.get(0))).thenReturn(testStatuses.get(0));
        sut.saveStatus(testStatuses.get(0));
    }

    /**
     * Tests that a RoomStatus can be updated and saved to the database.
     */
    @Test
    public void testUpdateStatus() {
        when(roomStatusRepository.save(testStatuses.get(0))).thenReturn(testStatuses.get(0));
        assertEquals(testStatuses.get(0), sut.updateStatus(testStatuses.get(0)));
    }
}
