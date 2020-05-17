package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.User;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

public interface ICallService {
    List<Call> getAll() throws ResourceNotFoundException;
    List<Call> getByUser(User user) throws ResourceNotFoundException;
    List<Call> getByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException;
    List<Call> getByUserMostCalled(User user) throws ResourceNotFoundException;
    Call newCall(final Call call) throws DataAccessException, ResourceNotFoundException;
}
