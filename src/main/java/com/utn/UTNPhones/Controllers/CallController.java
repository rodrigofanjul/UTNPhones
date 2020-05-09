package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.IncorrectPasswordException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/calls")
public class CallController {

    private final ICallService callService;

    @Autowired
    public CallController(ICallService callService) {
        this.callService = callService;
    }

    @PostMapping("/register")
    public Call Add(@Valid @RequestBody @NotNull Call call) throws IncorrectPasswordException {
        call = callService.registerCall(call);
        return call;
    }
}
