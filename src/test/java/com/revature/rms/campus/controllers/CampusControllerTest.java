package com.revature.rms.campus.controllers;

import com.revature.rms.campus.entities.*;
import com.revature.rms.core.exceptions.*;
import com.revature.rms.campus.services.CampusService;
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
 * These tests are for additional validation and to ensure the CampusService methods are working successfully when called
 * by the CampusController methods. See CampusServiceTests for more details on testing for CampusService methods.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CampusControllerTest {

    @InjectMocks
    private CampusController campusController;

    @Mock
    private CampusService campusService;

    List<Campus> testCampuses;
    Campus nullCampus;
    List<Campus> nullList = null;
    List<User> testUsers;
//    ErrorResponse err;
//    InvalidRequestException iiTest;
//    ResourceNotFoundException rnfTest;

    /**
     * This makes sure to refresh the data before each test to ensure each test has the same starting point.
     */
    @Before
    public void setUp() {
        //Campuses
        Campus c1 = new Campus(1, "University of South Florida", "USF", new Address(),1, 1, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3));
        Campus c2 = new Campus(2, "University of Central Florida", "UCF", new Address(),2, 2, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3));
        Campus c3 = new Campus(3, "University of North Florida", "UNF", new Address(),3, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3));
        Campus updatedCampus = new Campus(4, "North Florida University", "NFU", new Address(),3, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3));
        testCampuses = new ArrayList<>();
        testCampuses.add(c1);
        testCampuses.add(c2);
        testCampuses.add(c3);
        testCampuses.add(updatedCampus);

        //Users
        User u1 = new User(1, "David", "Spade");
        User u2 = new User(2, "Adam", "Sandler");
        User u3 = new User(3, "Kevin", "James");
        testUsers = new ArrayList<>();
        testUsers.add(u1);
        testUsers.add(u2);
        testUsers.add(u3);

//        //Exception Handling
//        err = new ErrorResponse();
//        iiTest = new InvalidRequestException("This is for testing");
//        rnfTest = new ResourceNotFoundException("This too... is for testing");
    }

    /**
     * This makes sure to erase any changes made to the data after each test and allow the GC to collect irrelevant data.
     */
    @After
    public void tearDown() {
        testCampuses = null;
        testUsers = null;
    }

    //************************************** SAVING *******************************************************


    /**
     * Tests whether a new campus was persisted to the database.
     */
    @Test
    public void testSaveNewCampus() {
        Campus newCampus = new Campus(5, "Campus of the Risen!", "CR", new Address(),2, 2, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3));
        when(campusService.save(newCampus)).thenReturn(newCampus);
        assertEquals(newCampus, campusController.saveCampus(newCampus));
    }

    /**
     * Tests that a Campus is not null before communicating with our database.
     */
    @Test (expected = InvalidRequestException.class)
    public void testSaveCampusFailed() {
        campusController.saveCampus(nullCampus);
    }

    //************************************** GETTING *******************************************************

    /**
     * Tests that all Campuses in our database can be retrieved.
     */
    @Test
    public void testGetAllCampuses() {
        when(campusService.findAll()).thenReturn(testCampuses);
        assertEquals(testCampuses, campusController.getAllCampuses());
    }

    /**
     * Tests that a specific Campus can be retrieved by it's ID.
     */
    @Test
    public void testGetCampusById() {
        when(campusService.findById(testCampuses.get(1).getId())).thenReturn(testCampuses.get(1));
        assertEquals(testCampuses.get(1), campusController.getCampusById(testCampuses.get(1).getId()));
    }

    /**
     * Tests that an InvalidRequestException will be thrown if an ID of 0 or less is passed.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetCampusByIdFailed() {
        int id = 0;
        campusController.getCampusById(id);
    }

    /**
     * Tests that all Campuses can be retrieved by their Training Manager ID.
     */
    @Test
    public void testGetCampusByTrainingManagerId() {
        when(campusService.findByTrainingManagerId(testUsers.get(0).getId())).thenReturn(Collections.singletonList(testCampuses.get(0)));
        assertEquals(Collections.singletonList(testCampuses.get(0)), campusController.getCampusByTrainingManagerId(testUsers.get(0).getId()));
    }

    /**
     * Tests that an InvalidRequestException is thrown when an ID of 0 or less is passed.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetCampusesByTrainingManagerId0() {
        campusController.getCampusByTrainingManagerId(0);
    }

    /**
     * Tests that a ResourceNotFoundException is thrown if no Campuses are returned.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testGetCampusesByTrainingManagerNotFound() {
        when(campusService.findByTrainingManagerId(50)).thenReturn(nullList);
        campusController.getCampusByTrainingManagerId(50);
    }

    /**
     * Tests that Campuses can be retrieved by their Staging Manager ID.
     */
    @Test
    public void testGetCampusByStagingManagerId() {
        when(campusService.findByStagingManagerId(testUsers.get(1).getId())).thenReturn(Collections.singletonList(testCampuses.get(1)));
        assertEquals(Collections.singletonList(testCampuses.get(1)), campusController.getCampusByStagingManagerId(testUsers.get(1).getId()));
    }

    /**
     * Tests that an InvalidRequestException is thrown when an ID of 0 or less is passed.
     */
    @Test(expected = InvalidRequestException.class)
    public void testGetCampusByStagingManagerId0() {
        campusController.getCampusByStagingManagerId(0);
    }

    /**
     * Tests that a ResourceNotFoundException is thrown if no campuses are retrieved by a Staging Manager ID
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testGetCampusByStagingManagerNotFound() {
        when(campusService.findByStagingManagerId(50)).thenReturn(nullList);
        campusController.getCampusByStagingManagerId(50);
    }

    /**
     * Tests that a Campus can be retrieved by it's resource owner ID.
     */
    @Test
    public void testGetByResourceOwnerId() {
        when(campusService.findByResourceOwnerId(testUsers.get(2).getId())).thenReturn(Collections.singletonList(testCampuses.get(2)));
        assertEquals(Collections.singletonList(testCampuses.get(2)), campusController.getByResourceOwnerId(testUsers.get(2).getId()));
    }

    //************************************** UPDATING *******************************************************

    /**
     * Tests that a Campus can be updated.
     */
    @Test
    public void testUpdateCampus() {
        when(campusService.update(testCampuses.get(2))).thenReturn(testCampuses.get(3));
        assertEquals(testCampuses.get(3), campusController.updateCampus(testCampuses.get(2)));
    }

    //************************************** DELETING *******************************************************

    /**
     * Tests that a Campus can be deleted by its ID.
     */
    @Test
    public void testDeleteCampusById() {
        when(campusService.delete(testCampuses.get(0).getId())).thenReturn(true);
        assertTrue(campusController.deleteCampusById(testCampuses.get(0).getId()));
    }

    /**
     * Tests that an InvalidRequestException will be thrown if an ID of 0 or less is passed.
     */
    @Test(expected = InvalidRequestException.class)
    public void testDeleteCampusFailed() {
        campusController.deleteCampusById(0);
    }

}
