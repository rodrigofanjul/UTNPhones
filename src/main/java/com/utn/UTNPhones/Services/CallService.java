package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.ICallRepository;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;
    private final IPhonelineService phonelineService;

    @Autowired
    public CallService(ICallRepository callRepository, IPhonelineService phonelineService) {
        this.callRepository = callRepository;
        this.phonelineService = phonelineService;
    }

    public List<Call> getUserCalls(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOrigin(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found for user (id:%d)",user.getId())));
    }

    public List<Call> getUserCallsBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOriginAndDateBetween(phonelineService.getByUser(user),start,end))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found for user (id:%d)",user.getId())));
    }

    public List<Call> getUserMostCalled(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findTopByOriginOrderByDestinationDesc(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found for user (id:%d)",user.getId())));
    }

    public Call registerCall(Call call) throws ResourceNotFoundException {
        call.setOrigin(phonelineService.getById(call.getOrigin().getId()));
        call.setDestination(phonelineService.getById(call.getDestination().getId()));
        return callRepository.save(call);
    }
}
