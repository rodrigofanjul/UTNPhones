package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.User;

import java.util.Date;
import java.util.List;

public interface IInvoiceService {
    List<Invoice> getAll() throws ResourceNotFoundException;
    List<Invoice> getByUser(User user) throws ResourceNotFoundException;
    List<Invoice> getByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException;
}
