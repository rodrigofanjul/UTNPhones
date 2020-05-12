package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.User;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

public interface ICallService {
    List<Call> getUserCalls(User user);
    List<Call> getUserCallsBetween(User user, Date start, Date end);
    List<Call> getUserMostCalled(User user);
    Call registerCall(final Call call) throws DataAccessException, ResourceNotFoundException;
}
