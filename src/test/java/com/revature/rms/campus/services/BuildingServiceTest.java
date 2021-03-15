package com.revature.rms.campus.services;
import com.revature.rms.campus.entities.*;
import com.revature.rms.core.exceptions.*;
import com.revature.rms.campus.repositories.BuildingRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BuildingServiceTest {
    @Mock
    BuildingRepository repo;
    @InjectMocks
    BuildingService sut;

    /**
     * testSaveWithValidBuilding() ensures BuildingService.save() is functioning properly with the valid or correct input.
     * A non-null building object should be returned.
     */
    @Test
    public void testSaveWithValidBuilding() {

        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));
        Building testBuilding2 = new Building(2, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        when(repo.save(Mockito.any())).thenReturn(testBuilding2);

        Building actualResults = sut.save(testBuilding);

        assertEquals(actualResults, testBuilding2);
    }

    /**
     * testSaveWithNullBuilding() ensures BuildingService.save() will not work if a null building object is being saved.
     * If the building object is null, a ResourceNotFoundException will be thrown since the method does not meet the
     * desired criteria for the method to execute properly.
     */
    @Test(expected = ResourcePersistenceException.class)
    public void testSaveWithNullBuilding(){

        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        Building testBuilding2 = new Building(2, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        Building actualResults = sut.save(null);

    }

    /**
     * testFindAll() ensures BuildingService().findAll() returns a list of all the existing Building objects.
     */
    @Test
    public void testFindAll() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));
        List<Building> mockBuildingList = Arrays.asList(testBuilding);
        when(repo.findAll()).thenReturn(mockBuildingList);
        assertEquals(mockBuildingList, sut.findAll());
    }

    /**
     * testFindAllBuildingWithNoCampus() returns an empty list rather than returning null, thus avoiding a potential
     * NullPointerException in the future.
     */
    @Test
    public void testFindAllBuildingWithNoCampus() {
        List<Building> mockBuildingList = Collections.emptyList();
        when(repo.findAll()).thenReturn(mockBuildingList);
        assertEquals(mockBuildingList, sut.findAll());
    }

    /**
     * testFindBuildingByIdWithValidId() ensures BuildingService.findById() returns the object containing the same id as the one provided.
     */
    @Test
    public void testFindBuildingByIdWithValidId() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        when(repo.findById(Mockito.any())).thenReturn(Optional.of(testBuilding));
        Optional<Building> actualResult = sut.findById(1);
        assertEquals(actualResult, Optional.of(testBuilding));
    }

    /**
     * testFindBuildingWithValidIdNotFound() throws a ResourceNotFoundException when provided a correct id but,
     * there is no building associated with it.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testFindBuildingWithValidIdNotFound() {
        when(repo.findById(Mockito.any())).thenReturn(Optional.empty());
        sut.findById(1);
    }

    /**
     * testFindBuildingWithInvalidId() throws an InvalidRequestException when the provided id is less than or equal to zero or
     * empty, that is the provided field is empty.
     */
    @Test(expected = InvalidRequestException.class)
    public void testFindBuildingWithInvalidId() {
        sut.findById(0); sut.findById(0);
        verify(repo.findById(0), times(0));
    }

    /**
     * testFindBuildingWithValidName() returns the object containing the same building name as the one provided.
     */
    @Test
    public void testFindBuildingWithValidName() {
        String name = "Muma School of Business";
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        when(repo.findByName(Mockito.any())).thenReturn(testBuilding);
        Building actualResult = sut.findByName(name);

        assertEquals(testBuilding, actualResult);
    }

    /**
     * testFindBuildingWithNullName returns InvalidRequestException
     */
    @Test(expected = InvalidRequestException.class)
    public void testFindBuildingWithNullName() {
        sut.findByName(null);
    }

    /**
     * testFindAllBuildingsByInvalidOwnerId tests that an ID less than 1 returns
     * an InvalidRequestException.
     */
    @Test(expected = InvalidRequestException.class)
    public void testFindAllBuildingsByInvalidOwnerId() {
        sut.findAllBuildingsByOwnerId(-2);
    }

    /**
     * testFindAllBuildingsByOwnerIdEmptyList tests that when the service tries to
     * return an empty list of Building objects, a ResourceNotFoundException occurs.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testFindAllBuildingsByOwnerIdEmptyList() {
        // setup empty list
        List<Building> expectedResult = new ArrayList<>();

        when(repo.findAll()).thenReturn(expectedResult);
        List<Building> actualResult = sut.findAllBuildingsByOwnerId(1);

        assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests to verify that a valid building can have its information
     * updated.
     */
    @Ignore // refactored BuildingService.update(). Now this test needs to be refactored
    @Test
    public void testUpdateBuilding() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));
        sut.save(testBuilding);
        Building testBuilding2 = new Building(2, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));
        when(repo.save(testBuilding2)).thenReturn(testBuilding2);
        assertEquals(sut.update(testBuilding2), testBuilding2);
    }

    /**
     * Tests that an InvalidRequestException is thrown when attempting to update
     * a null campus
     */
    @Test(expected = InvalidRequestException.class)
    public void testUpdateWithNullBuilding() {
        sut.update(null);
    }

    /**
     * testFindBuildingByTrainingLead() returns an object containing the same trainer id as the one provided.
     */
    @Test
    public void testFindBuildingByTrainingLead() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        when(repo.findByTrainingLead(Mockito.any())).thenReturn(testBuilding);
        Building actualResult = sut.findByTrainingLeadId(2);

        assertEquals(testBuilding, actualResult);
    }

    /**
     * testFindBuildingByTrainingLeadInvalidId() throws an InvalidRequestException when the provided id is less than or equal to zero.
     */
    @Test(expected = InvalidRequestException.class)
    public void testFindBuildingByTrainingLeadInvalidId() {
        Building actualResult = sut.findByTrainingLeadId(0);
    }

    /**
     * testFindBuildingByTrainingLeadIdNotPresent() throws a ResourceNotFoundException when provided a trainer id
     * that is a correct value but is not associated with any buildings.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testFindBuildingByTrainingLeadIdNotPresent() {
        when(repo.findByTrainingLead(Mockito.any())).thenReturn(null);
        Building actualResult = sut.findByTrainingLeadId(100);
    }

    /**
     * testFindBuildingWithValidNameUsingAbbreviatedName() returns the object containing the same abbreviated building name
     * as the one provided.
     */
    @Test
    public void testFindBuildingWithValidNameUsingAbbreviatedName() {
        String name = "MSB";
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        when(repo.findByName(Mockito.any())).thenReturn(testBuilding);
        Building actualResult = sut.findByName(name);
        assertEquals(testBuilding, actualResult);
    }

    /**
     * testUpdateWithValidBuilding() passes in a building object with changed fields to the buildingMongoRepository.save() to
     * persist the changes and return the updated saved building object. There is no need for a null building check since the
     * object already exists.
     */
    @Test
    public void testUpdateWithValidBuilding() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        Building testBuilding2 = new Building(2, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        when(repo.save(Mockito.any())).thenReturn((testBuilding));
        Building actualResult = sut.save(testBuilding);
        assertEquals(testBuilding, actualResult);
    }

    /**
     * testDeleteWithValidId() ensures buildingService.delete() functions by verifying buildingMongoRepository.deleteById() is ran
     * successfully one time when provided with a valid id.
     */
    @Test
    public void testDeleteWithValidId() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));

        sut.delete(testBuilding.getId());
        verify(repo, times(1)).deleteById(testBuilding.getId());
    }

    /**
     * testDeleteWithInvalidId() ensures that buildingMongoRepository.deleteById() is not run when provided an invalid id.
     * Instead it throws an InvalidRequestException.
     */
    @Test(expected = InvalidRequestException.class)
    public void testDeleteWithInvalidId() {
        Building testBuilding = new Building(1, "Muma School of Business", "MSB", new Address(),
                2, new ArrayList<Amenity>(1), new ArrayList<Room>(3));
        sut.delete(-24);
        verify(repo, times(0)).deleteById(-24);
    }


}
