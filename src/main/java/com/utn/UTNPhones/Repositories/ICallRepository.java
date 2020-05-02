package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ICallRepository extends JpaRepository<Call,Integer> {
    List<Call> findByDateBetween(Date start, Date end);
}
