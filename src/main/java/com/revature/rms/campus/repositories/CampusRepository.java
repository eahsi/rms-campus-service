package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.Campus;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepository extends CrudRepository<Campus, Integer> {

    /**
     * findByName method: The name parameter is passed as the input.
     * An Campus is returned when the input name matches a database record.
     * @param name Campus name String
     * @return Campus with matching name String
     */
    Campus findByName(String name);

    /**
     * findByTrainingManagerId method: The trainingManagerId parameter is passed as the input.
     * An Campus is returned when the input id matches a database record.
     * @param id trainingManagerId id int
     * @return Campus with matching id int
     */
    List<Campus> findByTrainingManagerId(Integer id);

    /**
     * findByStagingManagerId method: The stagingManagerId parameter is passed as the input.
     * An Campus is returned when the input id matches a database record.
     * @param id stagingManagerId id int
     * @return Campus with matching id int
     */
    List<Campus> findByStagingManagerId(Integer id);
}