package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IInvoiceRepository;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
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

    public List<Invoice> getAll() throws ResourceNotFoundException {
        return Optional.ofNullable(invoiceRepository.findAll())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Invoice not found")));
    }

    public List<Invoice> getUserInvoices(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(invoiceRepository.findByPhoneline(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Invoice not found for User with (id:%d)",user.getId())));
    }

    public List<Invoice> getUserInvoicesBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return Optional.ofNullable(invoiceRepository.findByPhonelineAndDateBetween(phonelineService.getByUser(user),start,end))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Invoice not found for User with (id:%d)",user.getId())));
    }
}
