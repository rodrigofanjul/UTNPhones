package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByIdcard(int idcard);
}