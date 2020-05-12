package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhonelineRepository extends JpaRepository<Phoneline,Integer> {
    Phoneline findById(Long id);
    Phoneline findByUser(User user);
}
