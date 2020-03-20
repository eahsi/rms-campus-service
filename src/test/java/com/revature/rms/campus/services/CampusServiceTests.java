package com.revature.rms.campus.services;

import com.revature.rms.campus.entities.Address;
import com.revature.rms.campus.entities.Building;
import com.revature.rms.campus.entities.Campus;
import com.revature.rms.campus.entities.ResourceMetadata;
import com.revature.rms.campus.exceptions.InvalidInputException;
import com.revature.rms.campus.exceptions.ResourceNotFoundException;
import com.revature.rms.campus.repositories.CampusMongoRepository;
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

/**
 * Testing for the CampusService class. The methods included in these tests are:
 * - testSaveWithValidCampus
 * - testSaveWithNullCampus
 * - testFindAll
 * - testFindAllCampusWithNoCampus
 * - testFindByCampusWithValidId
 * - testFindByCampusWithValidIdNotFound
 * - testFindByCampusWithInvalidId
 * - testFindCampusWithName
 * - testFindCampusWithNameUsingAbbreviatedName
 * - testUpdatedWithValidCampus
 * - testDeleteWithValidId
 * - testDeleteWithInvalidId
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CampusServiceTests {

    @Mock
    CampusMongoRepository repo;

    @InjectMocks
    CampusService sut;

    /**
     * Below are the tests for the CampusService.save(), this method should call campusMongoRepository.save and persist
     * the information taken from the user to the database. Field value checks for a campus object are handled inside of
     * the Campus POJO.
     */

    /**
     * This test ensures CampusService.save() is functioning properly with the proper provided information. A non-null
     * campus object should be returned.
     */
    @Test
    public void testSaveWithValidCampus() {

        Campus testCampus = new Campus("University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());

        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());

        when(repo.save(Mockito.any())).thenReturn(expectedResult);

        Campus actualResults = sut.save(testCampus);

        assertEquals(actualResults, expectedResult);
    }

    /**
     * This test ensures CampusService.save() will not work with if a null campus object is attempted to be saved. If
     * the campus object is null, a ResourceNotFoundException will be thrown since the method does not have the desired
     * object to function properly.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testSaveWithNullCampus() {

        Campus testCampus = new Campus("University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());

        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());

        Campus actualResults = sut.save(null);
    }

    /**
     * Below are the tests for the CampusService.findAll(). The function is to call campusMongoRepository.findAll() to
     * retrieve all Campus objects from the database.
     */

    /**
     * This test ensures CampusService.findAll() returns a list of all existing Campus objects.
     */
    @Test
    public void testFindAll() {
        Campus testCampus = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        List<Campus> mockCampusList = Arrays.asList(testCampus);
        when(repo.findAll()).thenReturn(mockCampusList);
        assertEquals(mockCampusList, sut.findAll());
    }

    /**
     * This test ensures CampusService.findAll() returns the empty list rather than returning null, thus avoiding
     * potential null pointer exceptions in the future.
     */
    @Test
    public void testFindAllCampusWithNoCampus() {
        List<Campus> mockCampusList = Collections.emptyList();
        when(repo.findAll()).thenReturn(mockCampusList);
        assertEquals(mockCampusList, sut.findAll());
    }

    /**
     * Below are the test for findCampusById(). This method's function is to call campusMongoRepository.findById to
     * retrieve a specific object from the database whose id matches the provided value.
     */

    /**
     * This test ensures CampusService.findById() returns the object containing the same id of the one provided
     */
    @Test
    public void findCampusByIdWithValidId() {
        Optional<Campus> expectedResult = Optional.of(new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata()));

        when(repo.findById(Mockito.any())).thenReturn(expectedResult);
        Optional<Campus> actualResult = sut.findById("32");
        assertEquals(actualResult, expectedResult);
    }

    /**
     * This test ensures that CampusService.findById() throws a ResourceNotFoundException when the provided id is a
     * correct value but there is no campus associated with it.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void findCampusWithValidIdNotFound() {
        when(repo.findById(Mockito.any())).thenReturn(Optional.empty());
        sut.findById("21");
    }

    /**
     * This test ensures that CampusService.findById throws an InvalidInputException when the provided id is a value
     * less than or equal to 0 or if the provided field is empty.
     */
    @Test(expected = InvalidInputException.class)
    public void findCampusWithInvalidId() {
        sut.findById(""); sut.findById("0");
        verify(repo.findById("0"), times(0));
    }

    /**
     * Below are tests related to findCampusByName(). The function of this method is to call campusMongoRepository.findByName
     * There is no current functionality in the application for this method. It was made as an additional feature that
     * may be helpful later on. It additionally serves as proof of
     * concept for testing.
     */

    /**
     * This test ensures that CampusService.findByName() returns a campus object when provided with a string name that
     * matches the name of a saved Campus object.
     */
    @Test
    public void findCampusWithValidName() {
        String name = "University of South Florida";
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());

        when(repo.findByName(Mockito.any())).thenReturn(expectedResult);
        Campus actualResult = sut.findByName(name);
        assertEquals(expectedResult, actualResult);
    }

    /**
     * This test ensures that CampusService.findByNames() returns a campus object when provided with a string name that
     * matches the abbreviated name for a campus object as well.
     */
    @Test
    public void findCampusWithValidNameUsingAbbreviatedName() {
        String name = "USF";
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        
        when(repo.findByName(Mockito.any())).thenReturn(expectedResult);
        Campus actualResult = sut.findByName(name);
        assertEquals(expectedResult, actualResult);
    }

    /**
     * The test below is for CampusService.update(). This method functions by passing in a campus object with changed
     * fields and calling campusMongoRepository.save to persist the changes and returns the updated saved campus object.
     * There is no null campus check since the campus will already be existing in order to update. Field value checks are
     * handled within the Campus POJO.
     */
    @Test
    public void findCampusByTrainingManagerId() {
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        when(repo.findByTrainingManagerId(Mockito.any())).thenReturn(expectedResult);
        Campus actualResult = sut.findByTrainingManagerId(2);
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findCampusByTrainingManagerIdWithNull() {
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        when(repo.findByTrainingManagerId(Mockito.any())).thenReturn(null);
        Campus actualResult = sut.findByTrainingManagerId(2);
    }

    @Test(expected = InvalidInputException.class)
    public void findCampusByTrainingManagerIdWithInvalidId() {
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        Campus actualResult = sut.findByTrainingManagerId(0);
    }

    @Test
    public void findCampusByStagingManagerId() {
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        when(repo.findByStagingManagerId(Mockito.any())).thenReturn(expectedResult);
        Campus actualResult = sut.findByStagingManagerId(3);
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findCampusByStagingManagerIdWithNull() {
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        when(repo.findByStagingManagerId(Mockito.any())).thenReturn(null);
        Campus actualResult = sut.findByStagingManagerId(2);
    }

    @Test(expected = InvalidInputException.class)
    public void findCampusByStagingManagerIdWithInvalidId() {
        Campus expectedResult = new Campus("32", "University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        Campus actualResult = sut.findByStagingManagerId(0);
    }

    @Test
    public void testUpdateWithValidCampus() {
        Campus testCampus = new Campus("32", "mocked", "m", new Address(),
                3, 4, 5, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());

        Campus expectedResult = new Campus("32","mocked", "m", new Address(),
                3, 4, 5, new ArrayList<Building>(2), new ArrayList<Integer>(4), new ResourceMetadata());

        when(repo.save(Mockito.any())).thenReturn((expectedResult));
        Campus actualResult = sut.save(testCampus);
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Below are the tests related to campusService.delete(). This method functions by taking a user provided id,
     * calling campusMongoRepository.deleteById with the provided id, and removing the campus object with the same id
     * from the database. Nothing is returned.
     */

    /**
     * This test ensures campusService.delete() functions by verifying campusMongoRepository.deleteById() is ran
     * successfully one time when provided with a valid id.
     */
    @Test
    public void testDeleteWithValidId() {
        Campus testCampus = new Campus("32","University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        sut.delete(testCampus.getId());
        verify(repo, times(1)).deleteById(testCampus.getId());
    }

    /**
     * This test ensures campusService.delete() functions by verifying campusMongoRepository.deleteById() is not run
     * when provided with an invalid id. The method instead will through an invalidInputException.
     */
    @Test(expected = InvalidInputException.class)
    public void testDeleteWithInvalidId() {
        Campus testCampus = new Campus("32","University of South Florida", "USF", new Address(),
                2, 3, 4, new ArrayList<Building>(1), new ArrayList<Integer>(3), new ResourceMetadata());
        sut.delete("-1");
        verify(repo, times(0)).deleteById("-1");
    }
}
