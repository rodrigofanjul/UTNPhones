package com.utn.phones.repository;

import com.utn.phones.model.Invoice;
import com.utn.phones.model.Phoneline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IInvoiceRepository extends JpaRepository<Invoice,Integer> {
    List<Invoice> findByPhonelineIn(Collection<Phoneline> tempPhoneline);
    List<Invoice> findByPhonelineInAndDateBetween(Collection<Phoneline> tempPhoneline, Date start, Date end);
}
