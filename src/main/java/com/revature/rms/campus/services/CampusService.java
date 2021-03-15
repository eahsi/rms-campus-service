package com.revature.rms.campus.services;


import com.revature.rms.campus.entities.Address;
import com.revature.rms.campus.entities.Campus;
import com.revature.rms.core.metadata.*;
import com.revature.rms.core.exceptions.*;
import com.revature.rms.campus.repositories.AddressRepository;
import com.revature.rms.campus.repositories.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The methods in this service call to methods from the campusRepository, addressRepository, metadataService and metadataRepository in order to give the basic CRUD features to
 * the application. The methods in this service are custom as a result of TDD.
 */
@Service
public class CampusService {

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     *  save Method: saves a Campus object to the database.
     *
     * @param campus Takes in a Campus object to be persisted to the database.
     * @return The persisted Campus object with its specific serialized id.
     */
    @Transactional
    public Campus save(Campus campus) {
        if (campus == null) {
            throw new InvalidRequestException("Cannot save null campus!");
        }

        if(campusRepository.findByName(campus.getName()) != null) {
            throw new ResourcePersistenceException("Campus with that name already exists");
        }

        Address address = addressRepository.save(campus.getShippingAddress());
        campus.setShippingAddress(address);
        Campus persisted = campusRepository.save(campus);
        return persisted;
    }

    /**
     * findAll Method: finds all of the campuses in the database.
     *
     * @return An arraylist of Campus Objects.
     */
    @Transactional(readOnly = true)
    public List<Campus> findAll() {
        Iterable<Campus> r = campusRepository.findAll();
        List<Campus> list = getListFromIterator(r);
        return list;
    }

    /**
     *  findById Method: finds a Campus by its ID.
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Campus findById(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }

        Optional<Campus> _campus = campusRepository.findById(id);
        if (!_campus.isPresent()) {
            throw new  ResourceNotFoundException("No campus found by that ID!");
        }

        return _campus.get();
    }

    /**
     * findByTrainingManagerId Method: finds a list of campuses that match the training manager ID field of a campus object.
     *
     * @param id Passes as a parameter an Integer of name id.
     * @return Returns a list of Campus Objects.
     */
    @Transactional(readOnly = true)
    public List<Campus> findByTrainingManagerId(Integer id) {
        if (id <= 0) {
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }
      
        List<Campus> campus = campusRepository.findByTrainingManagerId(id);

        if (campus.size() == 0) throw new ResourceNotFoundException("No campus found with training-manager id " + id);
        else return campus;
    }

    /**
     * findByStagingManagerId Method: finds a list of campuses that match the staging manager ID field of a campus object.
     *
     * @param id Passes as a parameter an Integer of name id.
     * @return Returns a list of Campus Objects.
     */
    @Transactional(readOnly = true)
    public List<Campus> findByStagingManagerId(Integer id) {
        if (id <= 0) {
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }

        List<Campus> campus = campusRepository.findByStagingManagerId(id);

        if (campus.size() == 0) throw new ResourceNotFoundException("No campus found with staging-manager id " + id);
        else return campus;
    }

    /**
     * findByResourceOwnerId method: finds a list of campuses that match the resource owner ID field of a campus object.
     *
     * @param id Id of the app user
     * @return List of campuses
     */
    @Transactional(readOnly = true)
    public List<Campus> findByResourceOwnerId(Integer id){

        if(id <= 0){
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }
        Iterable<Campus> allCampuses = campusRepository.findAll();
        List<Campus> campuses = new ArrayList<Campus>();
        for(Campus campus : allCampuses){
            ResourceMetadata data = campus.getResourceMetadata();
            if(data.getResourceOwner() == id){
                campuses.add(campus);
            }
        }
        if(campuses.isEmpty()){
            throw new ResourceNotFoundException("No campuses found for that owner!");
        }
        return campuses;
    }

    /**
     * findByName Method: finds a campus by its name.
     *
     * @param name Takes in a string of the campus name or abbreviation name.
     * @return Returns a campus Object.
     */
    @Transactional(readOnly = true)
    public Campus findByName(String name) {

        if (name == null || name == "") {
            throw new InvalidRequestException("Name cannot be null or empty!");
        }

        return campusRepository.findByName(name);
    }


    /**
     * update Method: updates fields of a campus object in the database.
     *
     * @param campus Campus object
     * @return Returns the modified Campus Object.
     */
    @Transactional
    public Campus update(Campus campus) {

        if (campus == null) {
            throw new InvalidRequestException("Campus cannot be null!");
        }

        return campusRepository.save(campus);
    }

    /**
     * delete Method: deletes a Campus object by its ID.
     *
     * @param id int value that is used to find the Campus Object.
     * @return Returns the boolean value of true.
     */
    @Transactional
    public boolean delete(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("ID cannot be less than or equal to zero!");
        }

        Optional<Campus> campus = campusRepository.findById(id);

        if (!campus.isPresent()) {
            throw new ResourceNotFoundException("No campus with id " + id + " was found!");
        }

        campus.get().getResourceMetadata().setCurrentlyActive(false);
        campus.get().getResourceMetadata().setLastModifiedDateTime(LocalDateTime.now().toString());

        campusRepository.save(campus.get());
        return true;
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
