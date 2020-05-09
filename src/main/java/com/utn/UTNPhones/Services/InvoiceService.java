package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IInvoiceRepository;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {

    private final IInvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(IInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAll() throws NotFoundException {
        List<Invoice> c = invoiceRepository.findAll();
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException());
    }

    public List<Invoice> getUserInvoices(User user) throws NotFoundException {
        Phoneline tempPhoneline = new Phoneline();
        tempPhoneline.setUser(user);
        List<Invoice> c = invoiceRepository.findByPhoneline(tempPhoneline);
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException());
    }

    public List<Invoice> getUserInvoicesBetween(User user, Date start, Date end) throws NotFoundException {
        Phoneline tempPhoneline = new Phoneline();
        tempPhoneline.setUser(user);
        List<Invoice> c = invoiceRepository.findByPhonelineAndDateBetween(tempPhoneline,start,end);
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException());
    }
}
