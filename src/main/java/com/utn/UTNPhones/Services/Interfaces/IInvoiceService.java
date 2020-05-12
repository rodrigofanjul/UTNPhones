package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.User;

import java.util.Date;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> getAll();
    List<Invoice> getUserInvoices(User user);
    List<Invoice> getUserInvoicesBetween(User user, Date start, Date end);
}
