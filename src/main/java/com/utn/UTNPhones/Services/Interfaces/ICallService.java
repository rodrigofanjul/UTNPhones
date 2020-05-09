package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.User;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

public interface ICallService {
    List<Call> getUserCalls(User user) throws NotFoundException;
    List<Call> getUserCallsBetween(User user, Date start, Date end) throws NotFoundException;
    List<Call> getUserMostCalled(User user) throws NotFoundException;
    Call registerCall(final Call call) throws DataAccessException;
}
