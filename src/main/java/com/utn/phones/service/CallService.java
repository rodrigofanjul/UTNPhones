package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.User;
import com.utn.phones.repository.ICallRepository;
import com.utn.phones.service.interfaces.ICallService;
import com.utn.phones.service.interfaces.IPhonelineService;
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

    public List<Call> getAll() throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findAll())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found.")));
    }

    public List<Call> getByUser(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOrigin(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found for user (id:%d)",user.getId())));
    }

    public List<Call> getByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOriginAndDateBetween(phonelineService.getByUser(user),start,end))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found for user (id:%d)",user.getId())));
    }

    public List<Call> getByUserMostCalled(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findTopByOriginOrderByDestinationDesc(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found for user (id:%d)",user.getId())));
    }

    public Call newCall(Call call) throws ResourceNotFoundException {
        call.setOrigin(phonelineService.getById(call.getOrigin().getId()));
        call.setDestination(phonelineService.getById(call.getDestination().getId()));
        return callRepository.save(call);
    }
}
