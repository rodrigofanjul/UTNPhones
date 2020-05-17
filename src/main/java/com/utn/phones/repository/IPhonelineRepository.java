package com.utn.phones.repository;

import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhonelineRepository extends JpaRepository<Phoneline,Integer> {
    Phoneline findById(Long id);
    Phoneline findByUser(User user);
}
