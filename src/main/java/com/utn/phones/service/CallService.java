package com.utn.phones.service;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import com.utn.phones.repository.ICallRepository;
import com.utn.phones.service.interfaces.ICallService;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames={"calls"})
@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;
    private final IPhonelineService phonelineService;

    @Autowired
    public CallService(ICallRepository callRepository, IPhonelineService phonelineService) {
        this.callRepository = callRepository;
        this.phonelineService = phonelineService;
    }

    @Cacheable
    public List<Call> getAll() {
        return callRepository.findAll();
    }

    @Cacheable
    public List<Call> getByPhoneline(Phoneline phoneline) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOrigin(phoneline))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found phoneline %d",phoneline.getId())));
    }

    @Cacheable
    public List<Call> getByUser(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOriginIn(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException("Resources Calls not found User %d",user.getId()));
    }

    @Cacheable
    public List<Call> getByPhonelineBetween(Phoneline phoneline, Date start, Date end) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOriginAndDateBetween(phoneline,start,end))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found phoneline %d",phoneline.getId())));
    }

    @Cacheable
    public List<Call> getByUserBetween(User user, Date start, Date end) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findByOriginInAndDateBetween(phonelineService.getByUser(user),start,end))
                .orElseThrow(() -> new ResourceNotFoundException("Resources Calls not found User %d",user.getId()));
    }

    public List<MostCalledDto> getByPhonelineMostCalled(Phoneline phoneline) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findTopByOriginOrderByDestinationCountDesc(phoneline))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources Calls not found phoneline %d",phoneline.getId())));
    }

    public List<MostCalledDto> getByUserMostCalled(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(callRepository.findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(user)))
                .orElseThrow(() -> new ResourceNotFoundException("Resources Calls not found User %d",user.getId()));
    }

    public Call newCall(Call call) throws ResourceNotFoundException {
        call.setOrigin(phonelineService.getById(call.getOrigin().getId()));
        call.setDestination(phonelineService.getById(call.getDestination().getId()));
        return callRepository.save(call);
    }
}
