package com.utn.phones.controller;

import com.utn.phones.model.Call;
import com.utn.phones.exception.ResourceNotFoundException;
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

    public List<Call> getCalls() throws ResourceNotFoundException {
        return callService.getAll();
    }

    public List<Call> getCallsByUser(User user) throws ResourceNotFoundException {
        return callService.getByUser(user);
    }

    public List<Call> getCallsByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return callService.getByUserBetween(user,start,end);
    }

    public List<Call> getCallsByUserMostCalled(User user) throws ResourceNotFoundException {
        return callService.getByUserMostCalled(user);
    }

    public Call newCall(Call call) throws ResourceNotFoundException {
        return callService.newCall(call);
    }
}
