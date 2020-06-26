package com.utn.phones.service;

import com.sun.org.apache.regexp.internal.RE;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import com.utn.phones.repository.IUserRepository;
import com.utn.phones.service.interfaces.ICityService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    IUserRepository userRepository;
    ICityService cityService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserService userService;

    User testUser;
    User testUser2;
    List<User> testUsers;

    @Before
    public void setUp() {
        userRepository = mock(IUserRepository.class);
        cityService = mock(ICityService.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository,cityService,bCryptPasswordEncoder);

        testUser = new User();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testUser2 = new User(2,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testUsers = Collections.singletonList(testUser);
    }

    @Test
    public void testGetAllOk() {
        when(userRepository.findAll()).thenReturn(testUsers);

        List<User> users = userService.getAll();

        assertEquals(1, users.size());
        assertEquals(Integer.valueOf(1), users.get(0).getId());
        assertEquals(Integer.valueOf(123), users.get(0).getIdcard());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetByIdOk() throws ResourceNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(testUser));

        User user = userService.getById(1);

        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userRepository, times(1)).findById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdNotFound() throws ResourceNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        userService.getById(1);
    }

    @Test
    public void testGetByIdCardOk() throws ResourceNotFoundException {
        when(userRepository.findByIdcard(123)).thenReturn(testUser);

        User user = userService.getByIdCard(123);

        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userRepository, times(1)).findByIdcard(123);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdCardNotFound() throws ResourceNotFoundException {
        when(userRepository.findByIdcard(123)).thenReturn(null);
        userService.getByIdCard(123);
    }

    @Test
    public void testNewUserOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(userRepository.findByIdcard(testUser.getIdcard())).thenReturn(null);

        User user = userService.newUser(testUser);

        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userRepository, times(1)).findByIdcard(testUser.getIdcard());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userRepository.findByIdcard(testUser.getIdcard())).thenReturn(null);
        when(cityService.getById(testUser.getCity().getId())).thenThrow(new ResourceNotFoundException());
        userService.newUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewUserAlreadyExists() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userRepository.findByIdcard(testUser.getIdcard())).thenReturn(testUser);
        userService.newUser(testUser);
    }

    @Test
    public void testUpdateUserOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userRepository.existsById(testUser.getId())).thenReturn(true);
        when(userRepository.findByIdcard(testUser.getIdcard())).thenReturn(null);
        when(userRepository.save(testUser)).thenReturn(testUser);

        User user = userService.updateUser(testUser);

        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals(Integer.valueOf(123), user.getIdcard());
        verify(userRepository, times(1)).existsById(testUser.getId());
        verify(userRepository, times(1)).findByIdcard(testUser.getIdcard());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userRepository.existsById(testUser.getId())).thenReturn(false);
        userService.updateUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testUpdateUserAlreadyExists() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userRepository.existsById(testUser.getId())).thenReturn(true);
        when(userRepository.findByIdcard(testUser.getIdcard())).thenReturn(testUser2);
        userService.updateUser(testUser);
    }

    @Test
    public void testDeleteUserOk() throws ResourceNotFoundException {
        when(userRepository.existsById(1)).thenReturn(true);
        userService.deleteUser(testUser);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserNotFound() throws ResourceNotFoundException {
        when(userRepository.existsById(1)).thenReturn(false);
        userService.deleteUser(testUser);
    }
}
