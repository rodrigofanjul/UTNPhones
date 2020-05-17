package com.utn.phones.repository;

import com.utn.phones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByIdcard(int idCard);
}