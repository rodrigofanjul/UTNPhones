package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.controller.PhonelineController;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.Phoneline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/phonelines")
public class PhonelineWebController {

    private final PhonelineController phonelineController;
    private final CallController callController;

    @Autowired
    public PhonelineWebController(PhonelineController phonelineController, CallController callController) {
        this.phonelineController = phonelineController;
        this.callController = callController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<List<Phoneline>> getPhonelines() throws ResourceNotFoundException {
        List<Phoneline> phonelines = phonelineController.getPhonelines();
        return (phonelines.size() > 0) ? ResponseEntity.ok(phonelines) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSelfPhonelineOrEmployee
    @GetMapping("/{id}")
    public ResponseEntity<Phoneline> getPhonelineById(@Validated @PathVariable @Min(1) Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(phonelineController.getPhonelineById(id));
    }

    @IsSelfPhonelineOrEmployee
    @GetMapping("/{id}/calls")
    public ResponseEntity<List<Call>> getPhonelineCalls(@Validated @PathVariable @Min(1) Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByPhoneline(phonelineController.getPhonelineById(id)));
    }

    @IsSelfPhonelineOrEmployee
    @GetMapping("/{id}/calls/between")
    public ResponseEntity<List<Call>> getPhonelineCallsBetween(@Validated @PathVariable @Min(1) Long id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByPhonelineBetween(phonelineController.getPhonelineById(id),start,end));
    }

    @IsSelfPhonelineOrEmployee
    @GetMapping("/{id}/calls/most-called")
    public ResponseEntity<List<MostCalledDto>> getPhonelineMostCalled(@Validated @PathVariable @Min(1) Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByPhonelineMostCalled(phonelineController.getPhonelineById(id)));
    }

    @IsEmployee
    @PostMapping
    public ResponseEntity<Phoneline> newPhoneline(@Valid @RequestBody @NotNull Phoneline phoneline) throws ResourceAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(phonelineController.newPhoneline(phoneline));
    }
}
