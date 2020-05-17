package com.utn.phones.controller;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.User;
import com.utn.phones.service.interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class InvoiceController {

    private final IInvoiceService invoiceService;

    @Autowired
    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public List<Invoice> getInvoices() throws ResourceNotFoundException {
        return invoiceService.getAll();
    }

    public List<Invoice> getInvoicesByUser(User user) throws ResourceNotFoundException {
        return invoiceService.getByUser(user);
    }

    public List<Invoice> getInvoicesByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return invoiceService.getByUserBetween(user,start,end);
    }
}
