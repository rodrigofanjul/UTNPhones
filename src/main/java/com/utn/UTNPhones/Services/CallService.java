package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.ICallRepository;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public List<Call> getUserCalls(User user) throws NotFoundException {
        Phoneline tempPhoneline = new Phoneline();
        tempPhoneline.setUser(user);
        List<Call> c = callRepository.findByOrigin(tempPhoneline);
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException());
    }

    public List<Call> getUserCallsBetween(User user, Date start, Date end) throws NotFoundException {
        Phoneline tempPhoneline = new Phoneline();
        tempPhoneline.setUser(user);
        List<Call> c = callRepository.findByOriginAndDateBetween(tempPhoneline,start,end);
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException());
    }

    public List<Call> getUserMostCalled(User user) throws NotFoundException {
        Phoneline tempPhoneline = new Phoneline();
        tempPhoneline.setUser(user);
        List<Call> c = callRepository.findTopByOriginOrderByDestinationDesc(tempPhoneline);
        return Optional.ofNullable(c).orElseThrow(() -> new NotFoundException());
    }

    public Call registerCall(Call call) throws DataAccessException {
        call.setDate(new Date());
        return callRepository.save(call);
    }
}
