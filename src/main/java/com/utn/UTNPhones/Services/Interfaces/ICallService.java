package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Call;

import java.util.Date;
import java.util.List;

public interface ICallService {
    List<Call> Query(Date start, Date end) throws NotFoundException;
}
