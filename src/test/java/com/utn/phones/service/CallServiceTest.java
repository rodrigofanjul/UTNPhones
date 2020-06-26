package com.utn.phones.service;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.repository.ICallRepository;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallServiceTest {

    ICallRepository callRepository;
    IPhonelineService phonelineService;
    CallService callService;
    Date testDate;
    Invoice testInvoice;
    Phoneline testPhonelineOrigin;
    Phoneline testPhonelineDestination;
    User testUser;
    Call testCall;
    List<Call> testCalls;
    MostCalledDto testMostCalledDto;
    List<MostCalledDto> testMostCalledDtos;

    @Before
    public void setUp() {
        callRepository = mock(ICallRepository.class);
        phonelineService = mock(IPhonelineService.class);
        callService = new CallService(callRepository,phonelineService);

        testDate = new Date();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhonelineOrigin = new Phoneline(1L,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testPhonelineDestination = new Phoneline(2L,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testInvoice = new Invoice(1,testPhonelineOrigin,1,1f,1.21f,testDate,false,testDate);
        testCall = new Call(1,testInvoice,testPhonelineOrigin,testPhonelineDestination,testDate,1.0f,10,1.0f);
        testCalls = Collections.singletonList(testCall);
        testMostCalledDto = new MostCalledDto(1L, 1L, 1L);
        testMostCalledDtos = Collections.singletonList(testMostCalledDto);
    }

    @Test
    public void testGetAllOk() {
        when(callRepository.findAll()).thenReturn(testCalls);

        List<Call> calls = callService.getAll();

        assertEquals(1, calls.size());
        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        assertEquals(testInvoice, calls.get(0).getInvoice());
        assertEquals(testPhonelineOrigin, calls.get(0).getOrigin());
        assertEquals(testPhonelineDestination, calls.get(0).getDestination());
        assertEquals(testDate, calls.get(0).getDate());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getRate());
        assertEquals(Integer.valueOf(10), calls.get(0).getDuration());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getPrice());
        verify(callRepository, times(1)).findAll();
    }

    @Test
    public void testGetByUserOk() throws ResourceNotFoundException {
        when(callRepository.findByOriginIn(phonelineService.getByUser(testUser))).thenReturn(testCalls);

        List<Call> calls = callService.getByUser(testUser);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        assertEquals(testInvoice, calls.get(0).getInvoice());
        assertEquals(testPhonelineOrigin, calls.get(0).getOrigin());
        assertEquals(testPhonelineDestination, calls.get(0).getDestination());
        assertEquals(testDate, calls.get(0).getDate());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getRate());
        assertEquals(Integer.valueOf(10), calls.get(0).getDuration());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getPrice());
        verify(callRepository, times(1)).findByOriginIn(phonelineService.getByUser(testUser));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserNotFound() throws ResourceNotFoundException {
        when(callRepository.findByOriginIn(phonelineService.getByUser(testUser))).thenReturn(null);
        callService.getByUser(testUser);
    }

    @Test
    public void testGetByPhonelineOk() throws ResourceNotFoundException {
        when(callRepository.findByOrigin(testPhonelineOrigin)).thenReturn(testCalls);

        List<Call> calls = callService.getByPhoneline(testPhonelineOrigin);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        assertEquals(testInvoice, calls.get(0).getInvoice());
        assertEquals(testPhonelineOrigin, calls.get(0).getOrigin());
        assertEquals(testPhonelineDestination, calls.get(0).getDestination());
        assertEquals(testDate, calls.get(0).getDate());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getRate());
        assertEquals(Integer.valueOf(10), calls.get(0).getDuration());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getPrice());
        verify(callRepository, times(1)).findByOrigin(testPhonelineOrigin);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByPhonelineNotFound() throws ResourceNotFoundException {
        when(callRepository.findByOrigin(testPhonelineOrigin)).thenReturn(null);
        callService.getByPhoneline(testPhonelineOrigin);
    }

    @Test
    public void testGetByUserBetweenOk() throws ResourceNotFoundException {
        when(callRepository.findByOriginInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate)).thenReturn(testCalls);

        List<Call> calls = callService.getByUserBetween(testUser,testDate,testDate);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        assertEquals(testInvoice, calls.get(0).getInvoice());
        assertEquals(testPhonelineOrigin, calls.get(0).getOrigin());
        assertEquals(testPhonelineDestination, calls.get(0).getDestination());
        assertEquals(testDate, calls.get(0).getDate());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getRate());
        assertEquals(Integer.valueOf(10), calls.get(0).getDuration());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getPrice());
        verify(callRepository, times(1)).findByOriginInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserBetweenNotFound() throws ResourceNotFoundException {
        when(callRepository.findByOriginInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate)).thenReturn(null);
        callService.getByUserBetween(testUser, testDate, testDate);
    }

    @Test
    public void testGetByPhonelineBetweenOk() throws ResourceNotFoundException {
        when(callRepository.findByOriginAndDateBetween(testPhonelineOrigin,testDate,testDate)).thenReturn(testCalls);

        List<Call> calls = callService.getByPhonelineBetween(testPhonelineOrigin,testDate,testDate);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        assertEquals(testInvoice, calls.get(0).getInvoice());
        assertEquals(testPhonelineOrigin, calls.get(0).getOrigin());
        assertEquals(testPhonelineDestination, calls.get(0).getDestination());
        assertEquals(testDate, calls.get(0).getDate());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getRate());
        assertEquals(Integer.valueOf(10), calls.get(0).getDuration());
        assertEquals(Float.valueOf(1.0f), calls.get(0).getPrice());
        verify(callRepository, times(1)).findByOriginAndDateBetween(testPhonelineOrigin,testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByPhonelineBetweenNotFound() throws ResourceNotFoundException {
        when(callRepository.findByOriginAndDateBetween(testPhonelineOrigin,testDate,testDate)).thenReturn(null);
        callService.getByPhonelineBetween(testPhonelineOrigin, testDate, testDate);
    }

    @Test
    public void testGetByUserMostCalledOk() throws ResourceNotFoundException {
        when(callRepository.findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(testUser))).thenReturn(testMostCalledDtos);

        List<MostCalledDto> mostCalledDto = callService.getByUserMostCalled(testUser);

        assertEquals(1, mostCalledDto.size());
        assertEquals(Long.valueOf(1), mostCalledDto.get(0).getDestination());
        assertEquals(Long.valueOf(1), mostCalledDto.get(0).getCalls());
        assertEquals(Long.valueOf(1), mostCalledDto.get(0).getSeconds());
        verify(callRepository, times(1)).findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(testUser));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserMostCalledNotFound() throws ResourceNotFoundException {
        when(callRepository.findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(testUser))).thenReturn(null);
        callService.getByUserMostCalled(testUser);
    }

    @Test
    public void testGetByPhonelineMostCalledOk() throws ResourceNotFoundException {
        when(callRepository.findTopByOriginOrderByDestinationCountDesc(testPhonelineOrigin)).thenReturn(testMostCalledDtos);

        List<MostCalledDto> mostCalledDto = callService.getByPhonelineMostCalled(testPhonelineOrigin);

        assertEquals(1, mostCalledDto.size());
        assertEquals(Long.valueOf(1), mostCalledDto.get(0).getDestination());
        assertEquals(Long.valueOf(1), mostCalledDto.get(0).getCalls());
        assertEquals(Long.valueOf(1), mostCalledDto.get(0).getSeconds());
        verify(callRepository, times(1)).findTopByOriginOrderByDestinationCountDesc(testPhonelineOrigin);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByPhonelineMostCalledNotFound() throws ResourceNotFoundException {
        when(callRepository.findTopByOriginOrderByDestinationCountDesc(testPhonelineOrigin)).thenReturn(null);
        callService.getByPhonelineMostCalled(testPhonelineOrigin);
    }

    @Test
    public void testNewCallOk() throws ResourceNotFoundException {
        when(phonelineService.getById(1L)).thenReturn(testPhonelineOrigin);
        when(phonelineService.getById(2L)).thenReturn(testPhonelineDestination);
        when(callRepository.save(testCall)).thenReturn(testCall);

        Call call = callService.newCall(testCall);

        assertEquals(Integer.valueOf(1), call.getId());
        assertEquals(testInvoice, call.getInvoice());
        assertEquals(testPhonelineOrigin, call.getOrigin());
        assertEquals(testPhonelineDestination, call.getDestination());
        assertEquals(testDate, call.getDate());
        assertEquals(Float.valueOf(1.0f), call.getRate());
        assertEquals(Integer.valueOf(10), call.getDuration());
        assertEquals(Float.valueOf(1.0f), call.getPrice());
        verify(phonelineService,times(1)).getById(1L);
        verify(phonelineService,times(1)).getById(2L);
        verify(callRepository, times(1)).save(testCall);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewCallNotFound() throws ResourceNotFoundException {
        when(phonelineService.getById(testCall.getOrigin().getId())).thenThrow(new ResourceNotFoundException());
        callService.newCall(testCall);
    }
}
