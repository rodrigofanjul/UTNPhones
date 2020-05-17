package com.utn.phones.repository;

import com.utn.phones.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRateRepository extends JpaRepository<Rate,Integer> {
}
