/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author kasun
 */
public class UserTest {
    private User user;
    public UserTest() {
        this.user = new User(
                    "Mike",
                    "liegh",
                    "Australia",
                    "email@email.com",
                    1234567892,
                    "MikeLeigh",
                    "password",
                    "Diploma");
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        String expResult = "Mike";
        String result = this.user.getFirstName();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        String expResult = "liegh";
        String result = this.user.getLastName();
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testGetAddress() {
        System.out.println("get Address");
        String expResult = "Australia";
        String result = this.user.getAddress();
        assertEquals(expResult, result);
    }   
    
    @Test
    public void testGetPhone() {
        System.out.println("get phone");
        int expResult = 1234567892;
        int result = this.user.getPhone();
        assertEquals(expResult, result);
    }   
    
     @Test
    public void testGetEmail() {
        System.out.println("get email");
        String expResult = "email@email.com";
        String result = this.user.getEmail();
        assertEquals(expResult, result);
    }  
    
    @Test
    public void testGetUserName() {
        System.out.println("get username");
        String expResult = "MikeLeigh";
        String result = this.user.getUsername();
        assertEquals(expResult, result);
    }  
    
    @Test
    public void testGetPassword() {
        System.out.println("get password");
        String expResult = "password";
        String result = this.user.getPassword();
        assertEquals(expResult, result);
    }  
}
