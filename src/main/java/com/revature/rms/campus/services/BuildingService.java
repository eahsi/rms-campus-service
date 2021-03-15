package com.revature.rms.campus.services;

import com.revature.rms.campus.entities.*;
import com.revature.rms.core.metadata.*;
import com.revature.rms.core.exceptions.*;

import com.revature.rms.campus.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The methods in this service call to methods from the buildingRepository in order to give the basic CRUD features to
 * the application. The methods in this service are custom as a result of TDD. For more information about the testing
 * see BuildingServiceTests.
 */
@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * Save Method: Saves a new building object to the database.
     *
     * @param building
     * @return The new saved building object
     */
    @Transactional
    public Building save(Building building) {

        if (building == null) {
            throw new ResourcePersistenceException("Null building cannot be saved!");
        }
        return buildingRepository.save(building);
    }

    /**
     * findAll method: returns a list of all the building objects in the database.
     *
     * @return a list of all the buildings
     */
    @Transactional(readOnly = true)
    public List<Building> findAll() {

        Iterable<Building> b = buildingRepository.findAll();
        List<Building> list = getListFromIterator(b);
        return list;

    }

    /**
     * findById Method: Finds a building using the buildingRepository and the
     * building's ID.
     *
     * @param id
     * @return the building object with the same building id as the input parameter.
     */
    @Transactional(readOnly = true)
    public Optional<Building> findById(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("Id cannot be less than or equal to zero!");
        }

        Optional<Building> theBuilding = buildingRepository.findById(id);
        if (!theBuilding.isPresent()) {
            throw new ResourceNotFoundException("No building found by that id!");
        }

        return theBuilding;
    }

    /**
     * findByName Method: This method finds a building by its name in the database.
     *
     * @param name
     * @return the room object with the same room number as the input parameter.
     */
    @Transactional(readOnly = true)
    public Building findByName(String name) {
        if (name == null) {
            throw new InvalidRequestException("Null value entered for name!");
        }
        return buildingRepository.findByName(name);
    }

    /**
     * findByBuildingOwnerId method: Retrieves list of Buildings owned by an app user
     *
     * @param id ID of the app user
     * @return List of buildings
     */
    @Transactional(readOnly = true)
    public List<Building> findAllBuildingsByOwnerId(Integer id){

        if(id <= 0){
            throw new InvalidRequestException("Id cannot be less than or equal to zero!");
        }
        Iterable<Building> allBuildings = buildingRepository.findAll();
        List<Building> buildings = new ArrayList<Building>();
        for (Building build : allBuildings){
            ResourceMetadata metadata = build.getResourceMetadata();
            if(metadata.getResourceOwner() == id){
                buildings.add(build);
            }
        }
        if(buildings.isEmpty()){
            throw new ResourceNotFoundException("The list of buildings is empty!");
        }
        return buildings;
    }

    /**
     * Update Method: The building object is inputted and changes are saved.
     * The modified object is returned.
     *
     * @param building
     * @return Updated/Modified room object
     */
    @Transactional
    public Building update(Building building) {

        if (building == null) {
            throw new InvalidRequestException("Null value entered for building!");
        }

        Building oldBuilding;
        oldBuilding = buildingRepository.findById(building.getId()).get();

        building.setCampus(oldBuilding.getCampus());
        building.setResourceMetadata(oldBuilding.getResourceMetadata());

        building.setResourceMetadata(oldBuilding.getResourceMetadata());

        building.getResourceMetadata().setLastModifiedDateTime(LocalDateTime.now().toString());

        // change to the logged in user's id when authentication is implemented
        building.getResourceMetadata().setLastModifier(oldBuilding.getResourceMetadata().getLastModifier());

        return buildingRepository.save(building);
    }

    /**
     * Delete Method: Deletes a building by its ID in the database.
     *
     * @param id
     * @return The deleted building object.
     */
    @Transactional
    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("Id cannot be less than or equal to zero!");
        }
        buildingRepository.deleteById(id);
    }

    /**
     * findByTrainingLeadId Method: Finds a building by the training lead's ID.
     *
     * @param id
     * @return The building object.
     */
    @Transactional(readOnly = true)
    public Building findByTrainingLeadId(int id) {
        if (id <= 0) throw new InvalidRequestException("Id cannot be less than or equal to zero!");
        Building temp = buildingRepository.findByTrainingLead(id);
        if (temp == null) throw new ResourceNotFoundException("No training lead found!");
        else return temp;
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
