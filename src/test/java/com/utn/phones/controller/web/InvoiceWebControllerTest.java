package com.utn.phones.controller.web;

import com.utn.phones.controller.InvoiceController;
import com.utn.phones.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InvoiceWebControllerTest {

    InvoiceController invoiceController;
    InvoiceWebController invoiceWebController;

    Invoice testInvoice;
    User testUser;
    Date testDate;
    List<Invoice> testInvoices;
    Phoneline testPhoneline;

    @Before
    public void setUp() {
        invoiceController = mock(InvoiceController.class);
        invoiceWebController = new InvoiceWebController(invoiceController);

        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1L,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testDate = new Date();
        testInvoice = new Invoice(1,testPhoneline,1,1f,1.21f,new Date(),false,new Date());
        testInvoices = Collections.singletonList(testInvoice);
    }

    @Test
    public void testGetInvoicesOk() {
        when(invoiceController.getInvoices()).thenReturn(testInvoices);

        ResponseEntity<List<Invoice>> response = invoiceWebController.getInvoices();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(Integer.valueOf(1), response.getBody().get(0).getId());
        assertEquals(testPhoneline, response.getBody().get(0).getPhoneline());
        verify(invoiceController, times(1)).getInvoices();
    }

    @Test
    public void testGetInvoicesNoContent() {
        when(invoiceController.getInvoices()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Invoice>> response = invoiceWebController.getInvoices();

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(invoiceController, times(1)).getInvoices();
    }
}
