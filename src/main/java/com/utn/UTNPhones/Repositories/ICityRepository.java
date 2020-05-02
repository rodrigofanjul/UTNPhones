package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICityRepository extends JpaRepository<City,Integer> {
}
