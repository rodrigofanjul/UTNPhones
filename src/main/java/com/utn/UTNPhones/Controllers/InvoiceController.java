package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        List<Invoice> invoices = invoiceService.getAll();
        if (invoices == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(invoices);
    }
}
