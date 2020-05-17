package com.utn.phones.controller.web;

import com.utn.phones.controller.InvoiceController;
import com.utn.phones.controller.UserController;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceWebController {

    private final InvoiceController invoiceController;
    private final UserController userController;

    @Autowired
    public InvoiceWebController(InvoiceController invoiceController, UserController userController) {
        this.invoiceController = invoiceController;
        this.userController = userController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        List<Invoice> invoices = invoiceController.getInvoices();
        return (invoices.size() > 0) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSameUserOrEmployee
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserInvoices(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userController.getUserById(id);
        List<Invoice> invoices = invoiceController.getInvoicesByUser(u);
        return ResponseEntity.ok(invoices);
    }

    @IsSameUserOrEmployee
    @GetMapping("/{id}/between")
    public ResponseEntity<Object> getUserInvoicesBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        User u = userController.getUserById(id);
        return ResponseEntity.ok(invoiceController.getInvoicesByUserBetween(u,start,end));
    }
}
