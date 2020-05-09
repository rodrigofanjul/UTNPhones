package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final IInvoiceService invoiceService;

    @Autowired
    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("")
    public List<Invoice> getAll() throws NotFoundException {
        List<Invoice> invoice = invoiceService.getAll();
        return invoice;
    }
}
