package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Exceptions.NullArgumentException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/call")
public class CallController {

    private final ICallService callService;

    @Autowired
    public CallController(ICallService callService) {
        this.callService = callService;
    }

    @PostMapping("/query/")
    public List<Call> Query(@NotNull Date start, @NotNull Date end) throws NotFoundException {
        List<Call> calls = callService.Query(start,end);
        return calls;
    }
}
