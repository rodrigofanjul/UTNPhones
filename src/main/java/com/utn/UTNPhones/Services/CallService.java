package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Repositories.ICallRepository;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;

    @Autowired
    public CallService(ICallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public List<Call> Query(Date start, Date end) throws NotFoundException {
        List<Call> c = callRepository.findByDateBetween(start,end);
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException("call",0));
    }
}
