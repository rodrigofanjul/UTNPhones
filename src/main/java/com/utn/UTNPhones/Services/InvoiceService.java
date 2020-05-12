package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IInvoiceRepository;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public List<Invoice> getUserInvoices(User user) {
        Phoneline phoneline = phonelineService.getByUser(user);
        return invoiceRepository.findByPhoneline(phoneline);
    }

    public List<Invoice> getUserInvoicesBetween(User user, Date start, Date end) {
        Phoneline phoneline = phonelineService.getByUser(user);
        return invoiceRepository.findByPhonelineAndDateBetween(phoneline,start,end);
    }
}
