package com.utn.phones.service.interfaces;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

public interface ICallService {
    List<Call> getAll() throws ResourceNotFoundException;
    List<Call> getByPhoneline(Phoneline phoneline) throws ResourceNotFoundException;
    List<Call> getByUser(User user) throws ResourceNotFoundException;
    List<Call> getByPhonelineBetween(Phoneline phoneline, Date start, Date end) throws ResourceNotFoundException;
    List<Call> getByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException;
    List<MostCalledDto> getByPhonelineMostCalled(Phoneline phoneline) throws ResourceNotFoundException;
    List<MostCalledDto> getByUserMostCalled(User user) throws ResourceNotFoundException;
    Call newCall(final Call call) throws DataAccessException, ResourceNotFoundException;
}
