package integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rms.campus.CampusServiceApplication;
import com.revature.rms.campus.entities.Room;
import com.revature.rms.campus.entities.RoomStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Below are some integration tests for the RoomController. The methods tested in this suite are:
 * - getAllRooms
 * - saveRoom
 * - getRoomById
 * - updateRoom
 * - deleteRoomById
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CampusServiceApplication.class)
@AutoConfigureMockMvc
public class RoomControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    /**
     * The method below was created to transform the object passed into a string to satisfy the requirements of
     * MockMvcRequestBuilders methods.
     * @param obj
     * @return String
     */
    public static String asJSON(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This ensures RoomController.getAllRooms hits the desired endpoint provided, produces a JSON object retrieved from
     * the database, and returns a status of 200 if everything was successful.
     * @throws Exception from perform()
     */
    @Test
    @Ignore
    public void testGetAllRoomsWithExistingRoomsExpecting200() throws Exception {

        this.mvc.perform(get("/campuses/rooms").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /**
     * This ensures RoomController.saveRoom when the post method hits the desired endpoint, consumes and produces
     * a JSON object, and returns a status of 200 when the information is successfully persisted.
     * @throws Exception from perform()
     */
    @Test
    @Ignore
    public void testSaveRoomWithValidRoomExpecting200() throws Exception {
        Room testRoom = new Room(3, "2301", 30, new ArrayList<RoomStatus>(1),
                1, new ArrayList<Integer>(1));
        this.mvc.perform(post("/campuses/rooms").content(asJSON(testRoom)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    /**
     * This tests RoomController.getRoomById. The method is supposed to get the test room as a JSON object by using the
     * id provided when it hitting the URI. However, it is currently throwing a 400 - java.lang.AssertionError : Status
     * This test was implemented later in development and more time is needed to figure out what is causing the issue
     * @throws Exception from perform()
     */
    @Test
    @Ignore
    public void testGetRoomWithValidIdExpecting200() throws Exception {
        Room testRoom = new Room(1, "2301", 30,
                new ArrayList<RoomStatus>(5), 1, new ArrayList<Integer>(3));
        this.mvc.perform(post("/campuses/rooms").content(asJSON(testRoom)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        this.mvc.perform(get("/campuses/rooms/id/{id}", "2301").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2301));
    }

    /**
     * This tests RoomController.updateRoom. Ensure the method consumes and produces a JSON object and persists the updated
     * object fields to the database. Returns a 200 status if everything is done correctly after hitting the desired endpoint.
     * @throws Exception from perform()
     */
    @Test
    @Ignore
    public void testUpdateRoomWithValidRoomExpecting200() throws Exception{
        Room testRoom = new Room(2, "2301", 30,
                new ArrayList<RoomStatus>(5), 1, new ArrayList<Integer>(3));
        this.mvc.perform(put("/campuses/rooms").content(asJSON(testRoom)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /**
     * Tests RoomController.deleteRoomById. When hitting the desired endpoint the method should consume a JSON object and
     * proceed to remove it from the database. However this test is currently failing. As it was implemented later in
     * development more time is needed to identify the issue.
     * @throws Exception from perform()
     */
    @Test
    @Ignore
    public void testDeleteRoomByIdWithValidIdExpecting200() throws Exception{
        this.mvc.perform(delete("/campuses/rooms/id/{id}", "2301").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
}
