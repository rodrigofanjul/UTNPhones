package com.utn.UTNPhones.Repositories;

import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.Phoneline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.Date;
import java.util.List;

public interface ICallRepository extends JpaRepository<Call,Integer> {
    List<Call> findByOrigin(Phoneline tempPhoneline);
    List<Call> findByOriginAndDateBetween(Phoneline tempPhoneline, Date start, Date end);
    List<Call> findTopByOriginOrderByDestinationDesc(Phoneline tempPhoneline);
}
