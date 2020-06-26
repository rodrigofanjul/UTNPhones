package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import com.utn.phones.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
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

        testUser = new User();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testUsers = Collections.singletonList(testUser);
    }

    @Test
    public void testGetUsersOk() {
        when(userService.getAll()).thenReturn(testUsers);
        List<User> users = userController.getUsers();
        assertEquals(1, users.size());
        assertEquals(Integer.valueOf(1), users.get(0).getId());
        assertEquals(Integer.valueOf(123), users.get(0).getIdcard());
        verify(userService, times(1)).getAll();
    }

    @Test
    public void testGetUsersNoContent() {
        when(userService.getAll()).thenReturn(new ArrayList<>());
        List<User> response = userController.getUsers();
        assertEquals(0,response.size());
        verify(userService, times(1)).getAll();
    }

    @Test
    public void testGetUserOk() throws ResourceNotFoundException {
        when(userService.getById(1)).thenReturn(testUser);
        User user = userController.getUser(1);
        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userService, times(1)).getById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUserNotFound() throws ResourceNotFoundException {
        when(userService.getById(1)).thenThrow(new ResourceNotFoundException());
        userController.getUser(1);
    }

    @Test
    public void testGetUserCardOk() throws ResourceNotFoundException {
        when(userService.getByIdCard(123)).thenReturn(testUser);
        User user = userController.getUserCard(123);
        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userService, times(1)).getByIdCard(123);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUserCardNotFound() throws ResourceNotFoundException {
        when(userService.getByIdCard(123)).thenThrow(new ResourceNotFoundException());
        userController.getUserCard(123);
    }

    @Test
    public void testNewUserOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userService.newUser(testUser)).thenReturn(testUser);
        User user = userController.newUser(testUser);
        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userService, times(1)).newUser(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userService.newUser(testUser)).thenThrow(new ResourceNotFoundException());
        userController.newUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewUserAlreadyExists() throws ResourceAlreadyExistsException, ResourceNotFoundException {
        when(userService.newUser(testUser)).thenThrow(new ResourceAlreadyExistsException());
        userController.newUser(testUser);
    }

    @Test
    public void testUpdateUserOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userService.updateUser(testUser)).thenReturn(testUser);
        User user = userController.updateUser(testUser);
        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userService, times(1)).updateUser(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userService.updateUser(testUser)).thenThrow(new ResourceNotFoundException());
        userController.updateUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testUpdateUserAlreadyExists() throws ResourceAlreadyExistsException, ResourceNotFoundException {
        when(userService.updateUser(testUser)).thenThrow(new ResourceAlreadyExistsException());
        userController.updateUser(testUser);
    }

    @Test
    public void testDeleteUserOk() throws ResourceNotFoundException {
        userController.deleteUser(testUser);
        verify(userService, times(1)).deleteUser(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserNotFound() throws ResourceNotFoundException {
        doThrow(new ResourceNotFoundException()).when(userService).deleteUser(testUser);
        userController.deleteUser(testUser);
    }
}
