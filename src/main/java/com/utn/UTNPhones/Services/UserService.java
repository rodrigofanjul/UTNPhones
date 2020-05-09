package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Exceptions.IncorrectPasswordException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IUserRepository;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(User user) throws NotFoundException {
        User u = userRepository.findByIdcard(user.getIdcard());
        return Optional.ofNullable(u).orElseThrow(() -> new NotFoundException());
    }

    public User registerUser(User user) throws AlreadyExistsException, DataAccessException {
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
}