package com.revature.rms.campus.repositories;


import com.revature.rms.campus.entities.Building;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuildingRepositoryTest {

    @Autowired
    private BuildingRepository sut;


    @Test
    public void testFindAll(){

        Iterable<Building> result = sut.findAll();

        Assertions.assertNotNull(result);

    }

    @Test
    public void testFindById(){

        Optional<Building> _result = sut.findById(1);

        Building result = _result.get();
        int actualId = result.getId();

        assertEquals(1, actualId);

    }

    @Test
    public void testFindByName() {

        Building result = sut.findByName("North West Education Complex");
        String actualName = result.getName();

        assertEquals("North West Education Complex", actualName);

    }

    @Test
    public void testFindByTrainingLead() {

        Building result = sut.findByTrainingLead(5);
        int actualId = result.getTrainingLead();

        assertEquals(5, actualId);

    }

}
