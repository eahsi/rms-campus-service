package com.revature.rms.campus.repositories;

import com.revature.rms.campus.entities.Room;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.revature.rms.core.metadata.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)

public class RoomRepositoryTest {


    @Autowired
    private RoomRepository sut;

    @Test
    public void testFindByRoomNumber() {

        Optional<Room> _result = sut.findByRoomNumber("107");
        Room result = _result.get();

        String actualRoomNumber = result.getRoomNumber();

        assertEquals("107", actualRoomNumber);

    }

    @Test
    public void testFindByMaxOccupancy() {

        List<Room> resultList = sut.findByMaxOccupancy(24);
        Room result = resultList.get(1);

        int actualId = result.getId();

        assertEquals(4, actualId);
    }

    @AfterEach
    public void tearDown() throws Exception {

        this.sut.deleteAll();

    }
}
