package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IUserRepository;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User Login(User user) throws NotFoundException {
        User u = userRepository.findByIdcardAndPassword(user.getIdcard(), user.getPassword());
        return Optional.ofNullable(u).orElseThrow(() -> new NotFoundException("user",user.getId()));
    }

    public User Register(final User user) throws DataAccessException {
        userRepository.save(user);
        return user;
    }
}