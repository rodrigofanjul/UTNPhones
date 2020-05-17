package com.utn.phones.controller;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class InvoiceControllerTest {

    InvoiceService invoiceService;
    InvoiceController invoiceController;
    Invoice testInvoice;
    User testUser;
    Date testDate;
    List<Invoice> testInvoices;

    @Before
    public void setUp() {
        invoiceService = mock(InvoiceService.class);
        invoiceController = new InvoiceController(invoiceService);
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testDate = new Date();
        testInvoice = new Invoice(1,new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE),1,1f,1.21f,new Date(),false,new Date());
        testInvoices = new ArrayList<Invoice>();
        testInvoices.add(testInvoice);
    }

    @Test
    public void testGetInvoicesOk() throws ResourceNotFoundException {
        try {
            when(invoiceService.getAll()).thenReturn(testInvoices);
            List<Invoice> invoices = invoiceController.getInvoices();
            assertEquals(1, invoices.size());
            assertEquals(Integer.valueOf(1), invoices.get(0).getId());
            verify(invoiceService, times(1)).getAll();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetInvoicesNotFound() throws ResourceNotFoundException {
        when(invoiceService.getAll()).thenThrow(new ResourceNotFoundException());
        List<Invoice> invoices = invoiceController.getInvoices();
    }

    @Test
    public void testGetInvoicesByUserOk() throws ResourceNotFoundException {
        try {
            when(invoiceService.getByUser(testUser)).thenReturn(testInvoices);
            List<Invoice> invoices = invoiceController.getInvoicesByUser(testUser);
            assertEquals(1, invoices.size());
            assertEquals(Integer.valueOf(1), invoices.get(0).getId());
            verify(invoiceService, times(1)).getByUser(testUser);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetInvoicesByUserNotFound() throws ResourceNotFoundException {
        when(invoiceService.getByUser(testUser)).thenThrow(new ResourceNotFoundException());
        List<Invoice> invoice = invoiceController.getInvoicesByUser(testUser);
    }

    @Test
    public void testGetInvoicesByUserBetweenOk() throws ResourceNotFoundException {
        try {
            when(invoiceService.getByUserBetween(testUser,testDate,testDate)).thenReturn(testInvoices);
            List<Invoice> invoices = invoiceController.getInvoicesByUserBetween(testUser,testDate,testDate);
            assertEquals(1, invoices.size());
            assertEquals(Integer.valueOf(1), invoices.get(0).getId());
            verify(invoiceService, times(1)).getByUserBetween(testUser,testDate,testDate);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetInvoicesByUserBetweenNotFound() throws ResourceNotFoundException {
        when(invoiceService.getByUserBetween(testUser,testDate,testDate)).thenThrow(new ResourceNotFoundException());
        List<Invoice> invoice = invoiceController.getInvoicesByUserBetween(testUser,testDate,testDate);
    }
}
