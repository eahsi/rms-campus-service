package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.User;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * findByFirstName method: The firstName parameter is passed as the input.
     * An User is returned when the input firstName matches a database record.
     * @param firstName User firstName String
     * @return User with matching firstName String
     */
    User findByFirstName(String firstName);
}