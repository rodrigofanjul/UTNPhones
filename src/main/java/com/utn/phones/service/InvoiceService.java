package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.User;
import com.utn.phones.repository.IInvoiceRepository;
import com.utn.phones.service.interfaces.IInvoiceService;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {

    private final IInvoiceRepository invoiceRepository;
    private final IPhonelineService phonelineService;

    @Autowired
    public InvoiceService(IInvoiceRepository invoiceRepository, IPhonelineService phonelineService) {
        this.invoiceRepository = invoiceRepository;
        this.phonelineService = phonelineService;
    }

    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getByUser(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(invoiceRepository.findByPhonelineIn(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException("Resources Invoice not found for User with (id:%d)",user.getId()));
    }

    public List<Invoice> getByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return Optional.ofNullable(invoiceRepository.findByPhonelineInAndDateBetween(phonelineService.getByUser(user),start,end))
                .orElseThrow(() -> new ResourceNotFoundException("Resources Invoice not found for User with (id:%d)",user.getId()));
    }
}
