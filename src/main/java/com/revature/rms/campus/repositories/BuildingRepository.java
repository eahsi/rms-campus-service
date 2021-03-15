package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.Building;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Integer>{

     /**
      * findByName method: The name parameter is passed as the input.
      * A Building is returned when the input name matches a database record.
      * @param name Building name String
      * @return Building with matching name String
      */
     Building findByName(String name);

     /**
      * findByTrainingLead method: The trainingLeadId parameter is passed as the input.
      * An Building is returned when the input id matches a database record.
      * @param id trainingLeadId id int
      * @return Building with matching id int
      */
     Building findByTrainingLead(Integer id);
}

