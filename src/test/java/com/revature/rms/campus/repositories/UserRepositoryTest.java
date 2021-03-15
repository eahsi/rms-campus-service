//package com.revature.rms.campus.repositories;
//
//import com.revature.rms.campus.entities.User;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.junit.jupiter.api.Assertions.*;
//
////@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//    @BeforeAll
//    public void setUp() throws Exception {
//        User user1 = new User("Alice", "A");
//        User user2 = new User("Bob", "B");
//        //save product, verify has ID value after save
//        assertNull(user1.getId());
//        assertNull(user2.getId());//null before save
//        this.userMongoRepository.save(user1);
//        this.userMongoRepository.save(user2);
//        assertNotNull(user1.getId());
//        assertNotNull(user2.getId());
//    }
//    @Test
//    public void testFetchData(){
//        /*Test data retrieval*/
//        User userA = userMongoRepository.findByFirstName("Bob");
//        assertNotNull(userA);
//        /*Get all products, list should only have two*/
//        Iterable<User> users = userMongoRepository.findAll();
//        int count = 0;
//        for(User p : users){
//            count++;
//        }
//        assertEquals(count, 2);
//    }
//    @Test
//    public void testDataUpdate(){
//        /*Test update*/
//        User userB = userMongoRepository.findByFirstName("Bob");
//        userB.setLastName("C");
//        userMongoRepository.save(userB);
//        User userC= userMongoRepository.findByFirstName("Bob");
//        assertNotNull(userC);
//        assertEquals("C", userC.getLastName());
//    }
//    @AfterAll
//    public void tearDown() throws Exception {
//        this.userMongoRepository.deleteAll();
//    }
//}