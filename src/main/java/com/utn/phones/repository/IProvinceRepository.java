package com.utn.phones.repository;

import com.utn.phones.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProvinceRepository extends JpaRepository<Province,Integer> {
}
