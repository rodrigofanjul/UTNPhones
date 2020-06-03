package com.utn.phones.repository;

import com.utn.phones.dto.CityDto;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICityRepository extends JpaRepository<City, Integer> {
    List<CityDto> findByProvince(Province province);
}
