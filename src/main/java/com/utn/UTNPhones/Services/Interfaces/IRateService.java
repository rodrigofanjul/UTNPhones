package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Rate;

import java.util.List;

public interface IRateService {
    List<Rate> getAll() throws NotFoundException;
}
