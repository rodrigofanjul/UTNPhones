package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.User;

import java.util.Date;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> getAll() throws NotFoundException;
    List<Invoice> getUserInvoices(User user) throws NotFoundException;
    List<Invoice> getUserInvoicesBetween(User user, Date start, Date end) throws NotFoundException;
}
