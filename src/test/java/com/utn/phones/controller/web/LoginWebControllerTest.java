package com.utn.phones.controller.web;

import com.utn.phones.controller.UserController;
import com.utn.phones.exception.InvalidLoginException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import com.utn.phones.security.SecurityProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LoginWebControllerTest {
    UserController userController;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    SecurityProvider securityProvider;
    LoginWebController loginWebController;

    User testUser;

    @Before
    public void setUp() {
        userController = mock(UserController.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        securityProvider = mock(SecurityProvider.class);
        loginWebController = new LoginWebController(userController, bCryptPasswordEncoder, securityProvider);

        testUser = new User();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
    }

    @Test
    public void testLoginUserOk() throws ResourceNotFoundException, InvalidLoginException {
        when(userController.getUserCard(123)).thenReturn(testUser);
        when(bCryptPasswordEncoder.matches("123","123")).thenReturn(true);

        ResponseEntity<Object> response = loginWebController.loginUser(testUser);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(userController, times(1)).getUserCard(123);
        verify(securityProvider, times(1)).getToken("1","EMPLOYEE");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testLoginUserNotFound() throws ResourceNotFoundException, InvalidLoginException {
        when(userController.getUserCard(123)).thenThrow(new ResourceNotFoundException());
        loginWebController.loginUser(testUser);
    }

    @Test(expected = InvalidLoginException.class)
    public void testLoginUserInvalidLogin() throws ResourceNotFoundException, InvalidLoginException {
        when(userController.getUserCard(123)).thenReturn(testUser);
        when(bCryptPasswordEncoder.matches("123","123")).thenReturn(false);
        loginWebController.loginUser(testUser);
    }
}
