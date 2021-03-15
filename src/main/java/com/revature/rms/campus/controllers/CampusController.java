package com.revature.rms.campus.controllers;

import com.revature.rms.campus.entities.*;
import com.revature.rms.campus.services.CampusService;
import com.revature.rms.core.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campuses/campuses") // service name/controller name
public class CampusController {

    private CampusService campusService;

    @Autowired
    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    /**
     * saveCampus method: Takes in a campus object as the input.
     *
     * @param campus newly persisted campus object
     * @return the newly added campus object
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Campus saveCampus(@RequestBody Campus campus) {

        if (campus == null) {

            throw new InvalidRequestException("Campus cannot be null!");

        }

        return campusService.save(campus);

    }

    /**
     * getAllCampus method: Returns a list of all the campus objects in the database.
     *
     * @return a list of all the campuses
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Campus> getAllCampuses() { return campusService.findAll(); }

    /**
     * getCampusById method: Returns a campus object when the id int matches a record in the database.
     *
     * @param id campusId int value
     * @return a campus with matching id
     */
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Campus getCampusById(@PathVariable int id) {

        if (id <= 0) {

            throw new InvalidRequestException("ID cannot be less than or equal to zero!");

        }

        Campus campus = campusService.findById(id);

        return campus;

    }

    /**
     * getCampusByTrainingManagerId method: Returns a campus object
     * that matches a trainingLeadId int id.
     *
     * @param id trainingLeadId int value
     * @return a campus with matching trainerLeadId
     */
    @GetMapping(value = "/training-managers/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Campus> getCampusByTrainingManagerId(@PathVariable int id) {

        if (id <= 0) {

            throw new InvalidRequestException("ID cannot be less than or equal to zero!");

        }

        List<Campus> campus = campusService.findByTrainingManagerId(id);

        if (campus == null) {

            throw new ResourceNotFoundException("Campus cannot be null!");

        }

        return campus;
    }

    /**
     * getCampusByStagingManagerId method: Returns a campus object
     * that matches a stagingManagerId int id.
     *
     * @param id stagingManagerId int value
     * @return a campus with matching stagingManagerId
     */
    @GetMapping(value = "/staging-managers/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Campus> getCampusByStagingManagerId(@PathVariable int id) {

        if (id <= 0) {

            throw new InvalidRequestException("ID cannot be less than or equal to zero!");

        }

        List<Campus> campus = campusService.findByStagingManagerId(id);

        if (campus == null) {

            throw new ResourceNotFoundException("Campus cannot be null!");

        }

        return campus;

    }

    /**
     * getByResourceOwnerId method: Retrieves list of campuses that a specific app user owns
     *
     * @param id ID of the app user
     * @return List of campuses
     */
    @GetMapping(value = "/owners/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE) // owners is plural by convention; not sure if it makes more sense to make it owners
    public List<Campus> getByResourceOwnerId(@PathVariable int id){
        return campusService.findByResourceOwnerId(id);
    }


    /**
     * updateCampus method: The campus object is inputted and changes are saved.
     *
     * @param campus newly updated campus object
     * @return updated/modified campus object
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Campus updateCampus(@RequestBody Campus campus) { return campusService.update(campus); }


    /**
     * deleteCampusById method: The campus object is deleted based on its campusId int
     *
     * @param id campusId int value
     */
    @DeleteMapping(value = "/id/{id}")
    public boolean deleteCampusById(@PathVariable int id) {

        if(id <= 0) {

            throw new InvalidRequestException("ID cannot be less than or equal to zero!");

        }

        campusService.delete(id);

        return true;

    }

}
