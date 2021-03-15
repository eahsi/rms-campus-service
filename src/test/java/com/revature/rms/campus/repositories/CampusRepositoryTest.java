package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.Address;
import com.revature.rms.campus.entities.Campus;
import com.revature.rms.campus.entities.User;
import com.revature.rms.core.metadata.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CampusRepositoryTest {

    @Autowired
    private CampusRepository sut;

    @Test
    public void testFindByName() {

        Campus result = sut.findByName("University of South Florida");
        String actualName = result.getName();

        assertEquals("University of South Florida", actualName);

    }

    @Test
    public void testFindByTrainingManagerId() {

        List<Campus> resultList = sut.findByTrainingManagerId(3);
        Campus result = resultList.get(0);
        int actualId = result.getTrainingManagerId();

        assertEquals(3, actualId);

    }

    @Test
    public void testFindByStagingManagerId() {

        List<Campus> resultList = sut.findByStagingManagerId(2);
        Campus result = resultList.get(0);
        int actualId = result.getStagingManagerId();

        assertEquals(2, actualId);
    }


}