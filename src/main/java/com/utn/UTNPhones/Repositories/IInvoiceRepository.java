package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice,Integer> {
}
