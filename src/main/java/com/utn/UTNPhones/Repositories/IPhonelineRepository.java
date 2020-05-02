package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.Phoneline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhonelineRepository extends JpaRepository<Phoneline,Integer> {
}
