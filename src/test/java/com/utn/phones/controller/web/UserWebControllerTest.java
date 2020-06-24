package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.controller.UserController;
import com.utn.phones.controller.InvoiceController;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class UserWebControllerTest {
    UserController userController;
    InvoiceController invoiceController;
    CallController callController;
    UserWebController userWebController;

    User testUser;
    List<User> testUsers;
    Date testDate;
    Phoneline testPhoneline;
    Call testCall;
    List<Call> testCalls;
    MostCalledDto testMostCalledDto;
    List<MostCalledDto> testMostCalledDtos;
    Invoice testInvoice;
    List<Invoice> testInvoices;

    @Before
    public void setUp() {
        userController = mock(UserController.class);
        invoiceController = mock(InvoiceController.class);
        callController = mock(CallController.class);
        userWebController = new UserWebController(userController, invoiceController, callController);

        testUser = new User();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testUsers = Arrays.asList(testUser);
        testDate = new Date();
        testPhoneline = new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testCall = new Call(1,null,testPhoneline,testPhoneline,testDate,1.0f,10,1.0f);
        testCalls = Arrays.asList(testCall);
        testMostCalledDto = new MostCalledDto(1l,1l,1l);
        testMostCalledDtos = Arrays.asList(testMostCalledDto);
        testInvoice = new Invoice(1,new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE),1,1f,1.21f,new Date(),false,new Date());
        testInvoices = Arrays.asList(testInvoice);
    }

    @Test
    public void testGetUsersOk() throws ResourceNotFoundException {
        try {
            when(userController.getUsers()).thenReturn(testUsers);

            ResponseEntity<List<User>> response = userWebController.getUsers();
            List<User> users = response.getBody();

            assertEquals(1, users.size());
            assertEquals(Integer.valueOf(1), users.get(0).getId());
            assertEquals("apellido", users.get(0).getLastname());
            assertEquals("nombre", users.get(0).getName());
            assertEquals(Integer.valueOf(123), users.get(0).getIdcard());
            assertEquals(EMPLOYEE, users.get(0).getRole());
            verify(userController, times(1)).getUsers();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUsersNotFound() throws ResourceNotFoundException {
        when(userController.getUsers()).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<User>> response = userWebController.getUsers();
    }

    @Test
    public void testGetUserOk() throws ResourceNotFoundException {
        try {
            when(userController.getUser(1)).thenReturn(testUser);

            ResponseEntity<User> response = userWebController.getUser(1);
            User user = response.getBody();

            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals("apellido", user.getLastname());
            assertEquals("nombre", user.getName());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            assertEquals(EMPLOYEE, user.getRole());
            verify(userController, times(1)).getUser(1);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUserNotFound() throws ResourceNotFoundException {
        when(userController.getUser(1)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<User> response = userWebController.getUser(1);
    }

    @Test
    public void testGetCallsByUserOk() throws ResourceNotFoundException {
        try {
            when(callController.getCallsByUser(userController.getUser(1))).thenReturn(testCalls);

            ResponseEntity<List<Call>> response = userWebController.getUserCalls(1);
            List<Call> calls = response.getBody();

            assertEquals(1, calls.size());
            assertEquals(Integer.valueOf(1), calls.get(0).getId());
            verify(callController, times(1)).getCallsByUser(userController.getUser(1));
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByUser(userController.getUser(1))).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Call>> response = userWebController.getUserCalls(1);
    }

    @Test
    public void testGetCallsByUserBetweenOk() throws ResourceNotFoundException {
        try {
            when(callController.getCallsByUserBetween(userController.getUser(1),testDate,testDate)).thenReturn(testCalls);

            ResponseEntity<List<Call>> response = userWebController.getUserCallsBetween(1,testDate,testDate);
            List<Call> calls = response.getBody();

            assertEquals(1, calls.size());
            assertEquals(Integer.valueOf(1), calls.get(0).getId());
            verify(callController, times(1)).getCallsByUserBetween(userController.getUser(1),testDate,testDate);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserBetweenNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByUserBetween(userController.getUser(1),testDate,testDate)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Call>> response = userWebController.getUserCallsBetween(1,testDate,testDate);
    }

    @Test
    public void testGetCallsByUserMostCalledOk() throws ResourceNotFoundException {
        try {
            when(callController.getCallsByUserMostCalled(userController.getUser(1))).thenReturn(testMostCalledDtos);

            ResponseEntity<List<MostCalledDto>> response = userWebController.getCallsByUserMostCalled(1);
            List<MostCalledDto> callsDto = response.getBody();

            assertEquals(1, callsDto.size());
            assertEquals(testMostCalledDto, callsDto.get(0));
            verify(callController, times(1)).getCallsByUserMostCalled(userController.getUser(1));
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserMostCalledNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByUserMostCalled(userController.getUser(1))).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<MostCalledDto>> response = userWebController.getCallsByUserMostCalled(1);
    }

    @Test
    public void testGetInvoicesByUserOk() throws ResourceNotFoundException {
        try {
            when(invoiceController.getInvoicesByUser(userController.getUser(1))).thenReturn(testInvoices);

            ResponseEntity<List<Invoice>> response = userWebController.getUserInvoices(1);
            List<Invoice> invoices = response.getBody();

            assertEquals(1, invoices.size());
            assertEquals(Integer.valueOf(1), invoices.get(0).getId());
            verify(invoiceController, times(1)).getInvoicesByUser(userController.getUser(1));
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetInvoicesByUserNotFound() throws ResourceNotFoundException {
        when(invoiceController.getInvoicesByUser(userController.getUser(1))).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Invoice>> response = userWebController.getUserInvoices(1);
    }

    @Test
    public void testGetInvoicesByUserBetweenOk() throws ResourceNotFoundException {
        try {
            when(invoiceController.getInvoicesByUserBetween(userController.getUser(1),testDate,testDate)).thenReturn(testInvoices);

            ResponseEntity<List<Invoice>> response = userWebController.getUserInvoicesBetween(1,testDate,testDate);
            List<Invoice> invoices = response.getBody();

            assertEquals(1, invoices.size());
            assertEquals(Integer.valueOf(1), invoices.get(0).getId());
            verify(invoiceController, times(1)).getInvoicesByUserBetween(userController.getUser(1),testDate,testDate);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetInvoicesByUserBetweenNotFound() throws ResourceNotFoundException {
        when(invoiceController.getInvoicesByUserBetween(userController.getUser(1),testDate,testDate)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Invoice>> response = userWebController.getUserInvoicesBetween(1,testDate,testDate);
    }

    @Test
    public void testNewUserOk() throws ResourceNotFoundException {
        try {
            when(userController.newUser(testUser)).thenReturn(testUser);

            ResponseEntity<User> response = userWebController.newUser(testUser);
            User user = response.getBody();

            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals("apellido", user.getLastname());
            assertEquals("nombre", user.getName());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            assertEquals(EMPLOYEE, user.getRole());
            verify(userController, times(1)).newUser(testUser);
        }
        catch (ResourceNotFoundException | ResourceAlreadyExistsException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userController.newUser(testUser)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<User> response = userWebController.newUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewUserAlreadyExists() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userController.newUser(testUser)).thenThrow(new ResourceAlreadyExistsException());
        ResponseEntity<User> response = userWebController.newUser(testUser);
    }

    @Test
    public void testUpdateUserOk() throws ResourceNotFoundException {
        try {
            when(userController.updateUser(testUser)).thenReturn(testUser);

            ResponseEntity<User> response = userWebController.updateUser(testUser);
            User user = response.getBody();

            assertEquals(Integer.valueOf(1), user.getId());
            assertEquals("apellido", user.getLastname());
            assertEquals("nombre", user.getName());
            assertEquals(Integer.valueOf(123), user.getIdcard());
            assertEquals(EMPLOYEE, user.getRole());
            verify(userController, times(1)).updateUser(testUser);
        }
        catch (ResourceNotFoundException | ResourceAlreadyExistsException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userController.updateUser(testUser)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<User> response = userWebController.updateUser(testUser);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testUpdateUserAlreadyExists() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(userController.updateUser(testUser)).thenThrow(new ResourceAlreadyExistsException());
        ResponseEntity<User> response = userWebController.updateUser(testUser);
    }

    @Test
    public void testDeleteUserOk() throws ResourceNotFoundException {
        try {
            userWebController.deleteUser(testUser);
            verify(userController, times(1)).deleteUser(testUser);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserNotFound() throws ResourceNotFoundException {
        doThrow(new ResourceNotFoundException()).when(userController).deleteUser(testUser);
        userWebController.deleteUser(testUser);
    }
}
