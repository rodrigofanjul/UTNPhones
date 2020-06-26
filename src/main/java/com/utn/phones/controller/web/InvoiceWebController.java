package com.utn.phones.controller.web;

import com.utn.phones.controller.InvoiceController;
import com.utn.phones.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.utn.phones.security.SecurityConfig.IsEmployee;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceWebController {

    private final InvoiceController invoiceController;

    @Autowired
    public InvoiceWebController(InvoiceController invoiceController) {
        this.invoiceController = invoiceController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<List<Invoice>> getInvoices() {
        List<Invoice> invoices = invoiceController.getInvoices();
        return (invoices.size() > 0) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}