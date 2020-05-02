package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRateRepository extends JpaRepository<Rate,Integer> {
}
