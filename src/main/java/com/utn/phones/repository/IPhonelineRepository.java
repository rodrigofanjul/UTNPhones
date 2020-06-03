package com.utn.phones.repository;

import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPhonelineRepository extends JpaRepository<Phoneline,Integer> {
    Phoneline findById(Long id);
    List<Phoneline> findByUser(User user);
}
