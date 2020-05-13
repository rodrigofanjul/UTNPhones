package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.ICallRepository;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;
    private final IPhonelineService phonelineService;

    @Autowired
    public CallService(ICallRepository callRepository, IPhonelineService phonelineService) {
        this.callRepository = callRepository;
        this.phonelineService = phonelineService;
    }

    public List<Call> getUserCalls(User user) {
        Phoneline phoneline = phonelineService.getByUser(user);
        return callRepository.findByOrigin(phoneline);
    }

    public List<Call> getUserCallsBetween(User user, Date start, Date end) {
        Phoneline phoneline = phonelineService.getByUser(user);
        return callRepository.findByOriginAndDateBetween(phoneline,start,end);
    }

    public List<Call> getUserMostCalled(User user) {
        Phoneline phoneline = phonelineService.getByUser(user);
        return callRepository.findTopByOriginOrderByDestinationDesc(phoneline);
    }

    public Call registerCall(Call call) throws ResourceNotFoundException {
        Phoneline origin = phonelineService.getById(call.getOrigin().getId());
        if(origin == null)
            throw new ResourceNotFoundException(String.format("Resource Phoneline not found with (id:%d)", call.getOrigin().getId()));

        Phoneline destination = phonelineService.getById(call.getDestination().getId());
        if(destination == null)
            throw new ResourceNotFoundException(String.format("Resource Phoneline not found with (id:%d)", call.getDestination().getId()));
        call.setOrigin(origin);
        call.setDestination(destination);
        return callRepository.save(call);
    }
}
