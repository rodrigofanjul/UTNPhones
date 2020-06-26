package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.repository.IInvoiceRepository;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InvoiceServiceTest {

    IInvoiceRepository invoiceRepository;
    IPhonelineService phonelineService;
    InvoiceService invoiceService;

    Date testDate;
    Invoice testInvoice;
    Phoneline testPhoneline;
    User testUser;
    List<Invoice> testInvoices;

    @Before
    public void setUp() {
        invoiceRepository = mock(IInvoiceRepository.class);
        phonelineService = mock(IPhonelineService.class);
        invoiceService = new InvoiceService(invoiceRepository,phonelineService);

        testDate = new Date();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1L,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testInvoice = new Invoice(1,testPhoneline,1,1.0f,1.21f,testDate,false,testDate);
        testInvoices = Collections.singletonList(testInvoice);
    }

    @Test
    public void testGetAllOk() {
        when(invoiceRepository.findAll()).thenReturn(testInvoices);

        List<Invoice> invoices = invoiceService.getAll();

        assertEquals(1, invoices.size());
        assertEquals(Integer.valueOf(1), invoices.get(0).getId());
        assertEquals(testPhoneline, invoices.get(0).getPhoneline());
        assertEquals(Integer.valueOf(1), invoices.get(0).getCallsQuantity());
        assertEquals(Float.valueOf(1.0f), invoices.get(0).getCostPrice());
        assertEquals(Float.valueOf(1.21f), invoices.get(0).getTotalPrice());
        assertEquals(testDate, invoices.get(0).getDate());
        assertEquals(Boolean.FALSE, invoices.get(0).getIsPaid());
        assertEquals(testDate, invoices.get(0).getExpirationDate());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    public void testGetByUserOk() throws ResourceNotFoundException {
        when(invoiceRepository.findByPhonelineIn(phonelineService.getByUser(testUser))).thenReturn(testInvoices);

        List<Invoice> invoices = invoiceService.getByUser(testUser);

        assertEquals(1, invoices.size());
        assertEquals(Integer.valueOf(1), invoices.get(0).getId());
        assertEquals(testPhoneline, invoices.get(0).getPhoneline());
        assertEquals(Integer.valueOf(1), invoices.get(0).getCallsQuantity());
        assertEquals(Float.valueOf(1.0f), invoices.get(0).getCostPrice());
        assertEquals(Float.valueOf(1.21f), invoices.get(0).getTotalPrice());
        assertEquals(testDate, invoices.get(0).getDate());
        assertEquals(Boolean.FALSE, invoices.get(0).getIsPaid());
        assertEquals(testDate, invoices.get(0).getExpirationDate());
        verify(invoiceRepository, times(1)).findByPhonelineIn(phonelineService.getByUser(testUser));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserNotFound() throws ResourceNotFoundException {
        when(invoiceRepository.findByPhonelineIn(phonelineService.getByUser(testUser))).thenReturn(null);
        invoiceService.getByUser(testUser);
    }

    @Test
    public void testGetByUserBetweenOk() throws ResourceNotFoundException {
        when(invoiceRepository.findByPhonelineInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate)).thenReturn(testInvoices);

        List<Invoice> invoices = invoiceService.getByUserBetween(testUser,testDate,testDate);

        assertEquals(1, invoices.size());
        assertEquals(Integer.valueOf(1), invoices.get(0).getId());
        assertEquals(testPhoneline, invoices.get(0).getPhoneline());
        assertEquals(Integer.valueOf(1), invoices.get(0).getCallsQuantity());
        assertEquals(Float.valueOf(1.0f), invoices.get(0).getCostPrice());
        assertEquals(Float.valueOf(1.21f), invoices.get(0).getTotalPrice());
        assertEquals(testDate, invoices.get(0).getDate());
        assertEquals(Boolean.FALSE, invoices.get(0).getIsPaid());
        assertEquals(testDate, invoices.get(0).getExpirationDate());
        verify(invoiceRepository, times(1)).findByPhonelineInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserBetweenNotFound() throws ResourceNotFoundException {
        when(invoiceRepository.findByPhonelineInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate)).thenReturn(null);
        invoiceService.getByUserBetween(testUser, testDate, testDate);
    }
}
