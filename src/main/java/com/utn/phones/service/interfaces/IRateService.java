package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Rate;

import java.util.List;

public interface IRateService {
    List<Rate> getAll();
}
