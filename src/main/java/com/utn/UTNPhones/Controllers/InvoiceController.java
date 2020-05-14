package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final IInvoiceService invoiceService;

    @Autowired
    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("")
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        List<Invoice> invoices = invoiceService.getAll();
        return ResponseEntity.ok(invoices);
    }
}
