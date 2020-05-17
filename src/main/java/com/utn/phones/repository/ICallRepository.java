package com.utn.phones.repository;

import com.utn.phones.model.Call;
import com.utn.phones.model.Phoneline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ICallRepository extends JpaRepository<Call,Integer> {
    List<Call> findByOrigin(Phoneline tempPhoneline);
    List<Call> findByOriginAndDateBetween(Phoneline tempPhoneline, Date start, Date end);
    List<Call> findTopByOriginOrderByDestinationDesc(Phoneline tempPhoneline);
}
