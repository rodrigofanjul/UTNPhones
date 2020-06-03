package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import static com.utn.phones.model.User.Role.*;
import com.utn.phones.service.UserService;

import org.junit.Test;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    UserService userService;
    UserController userController;
    User testUser;
    List<User> testUsers;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);

        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testUsers = Arrays.asList(testUser);
    }

    @Test
    public void testGetUsersOk() throws ResourceNotFoundException {
        try {
            when(userService.getAll()).thenReturn(testUsers);
            List<User> users = userController.getUsers();
            assertEquals(1, users.size());
            assertEquals(Integer.valueOf(1), users.get(0).getId());
            assertEquals(Integer.valueOf(123), users.get(0).getIdcard());
            verify(userService, times(1)).getAll();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUsersNotFound() throws ResourceNotFoundException {
        when(userService.getAll()).thenThrow(new ResourceNotFoundException());
        List<User> users = userController.getUsers();
    }

    @Test
    public void testGetUserByIdOk() throws ResourceNotFoundException {
        try {
            when(userService.getById(1)).thenReturn(testUser);
            User user = userController.getUserById(1);
            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            verify(userService, times(1)).getById(1);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUserByIdNotFound() throws ResourceNotFoundException {
        when(userService.getById(1)).thenThrow(new ResourceNotFoundException());
        User user = userController.getUserById(1);
    }

    @Test
    public void testGetUserByIdCardOk() throws ResourceNotFoundException {
        try {
            when(userService.getByIdCard(123)).thenReturn(testUser);
            User user = userController.getUserByIdCard(123);
            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            verify(userService, times(1)).getByIdCard(123);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUserByIdCardNotFound() throws ResourceNotFoundException {
        when(userService.getByIdCard(123)).thenThrow(new ResourceNotFoundException());
        User user = userController.getUserByIdCard(123);
    }

    @Test
    public void testNewUserOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        try {
            when(userService.newUser(testUser)).thenReturn(testUser);
            User user = userController.newUser(testUser);
            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            verify(userService, times(1)).newUser(testUser);
        }
        catch (ResourceNotFoundException | ResourceAlreadyExistsException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userService.newUser(testUser)).thenThrow(new ResourceNotFoundException());
        User user = userController.newUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewUserAlreadyExists() throws ResourceAlreadyExistsException, ResourceNotFoundException {
        when(userService.newUser(testUser)).thenThrow(new ResourceAlreadyExistsException());
        User user = userController.newUser(testUser);
    }

    @Test
    public void testUpdateUserOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        try {
            when(userService.updateUser(testUser)).thenReturn(testUser);
            User user = userController.updateUser(testUser);
            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            verify(userService, times(1)).updateUser(testUser);
        }
        catch (ResourceNotFoundException | ResourceAlreadyExistsException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userService.updateUser(testUser)).thenThrow(new ResourceNotFoundException());
        User user = userController.updateUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testUpdateUserAlreadyExists() throws ResourceAlreadyExistsException, ResourceNotFoundException {
        when(userService.updateUser(testUser)).thenThrow(new ResourceAlreadyExistsException());
        User user = userController.updateUser(testUser);
    }
}
