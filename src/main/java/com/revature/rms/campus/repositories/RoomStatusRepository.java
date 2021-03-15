package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.RoomStatus;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomStatusRepository extends CrudRepository<RoomStatus, Integer> {

    /**
     * findAllStatusBySubmitter Method: The submitter id is inputted as
     * the search criteria. A list of room status objects that have the
     * same submitter id as the given parameter will be returned.
     * Allows us, to easily compile a list of room statuses that were
     * submitted by a specific user.
     * @param id
     * @return the list of room status objects submitted by the given user id.
     */
    List<RoomStatus> findAllBySubmitterId(int submitterId);

    /**
     * findAllBySubmittedDateTime Method: A date of type String is passed into the method.
     * The given date is then used to find all room status objects that have the same
     * date as there submitted date parameter.
     * Allows us, to easily compile a list of room statuses that were submitted on a
     * particular date.
     * @param date
     * @return the list of room status objects with the specified submitted date
     */
    List<RoomStatus> findAllBySubmittedDateTime(String date);
}