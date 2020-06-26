package com.utn.phones.controller;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.model.Call;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import com.utn.phones.service.interfaces.ICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class CallController {

    private final ICallService callService;

    @Autowired
    public CallController(ICallService callService) {
        this.callService = callService;
    }

    public List<Call> getCalls() {
        return callService.getAll();
    }

    public List<Call> getCallsByUser(User user) throws ResourceNotFoundException {
        return callService.getByUser(user);
    }

    public List<Call> getCallsByPhoneline(Phoneline phoneline) throws ResourceNotFoundException {
        return callService.getByPhoneline(phoneline);
    }

    public List<Call> getCallsByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return callService.getByUserBetween(user,start,end);
    }

    public List<Call> getCallsByPhonelineBetween(Phoneline phoneline, Date start, Date end) throws ResourceNotFoundException {
        return callService.getByPhonelineBetween(phoneline,start,end);
    }

    public List<MostCalledDto> getCallsByUserMostCalled(User user) throws ResourceNotFoundException {
        return callService.getByUserMostCalled(user);
    }

    public List<MostCalledDto> getCallsByPhonelineMostCalled(Phoneline phoneline) throws ResourceNotFoundException {
        return callService.getByPhonelineMostCalled(phoneline);
    }

    public Call newCall(Call call) throws ResourceNotFoundException {
        return callService.newCall(call);
    }
}
