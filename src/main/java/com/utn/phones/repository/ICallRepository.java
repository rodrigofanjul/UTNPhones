package com.utn.phones.repository;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.model.Call;
import com.utn.phones.model.Phoneline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ICallRepository extends JpaRepository<Call,Integer> {
    List<Call> findByOrigin(Phoneline phoneline);
    List<Call> findByOriginIn(Collection<Phoneline> phonelines);
    List<Call> findByOriginAndDateBetween(Phoneline phoneline, Date start, Date end);
    List<Call> findByOriginInAndDateBetween(Collection<Phoneline> phonelines, Date start, Date end);

    @Query("SELECT new com.utn.phones.dto.MostCalledDto(destination.id, COUNT(destination), SUM(duration)) " +
            "FROM Call c WHERE origin = :#{#phoneline} " +
            "GROUP BY destination ORDER BY COUNT(destination) DESC")
    List<MostCalledDto> findTopByOriginOrderByDestinationCountDesc(@Param("phoneline") Phoneline phoneline);

    @Query("SELECT new com.utn.phones.dto.MostCalledDto(destination.id, COUNT(destination), SUM(duration)) " +
            "FROM Call c WHERE origin IN :#{#phonelines} " +
            "GROUP BY destination ORDER BY COUNT(destination) DESC")
    List<MostCalledDto> findTopByOriginInOrderByDestinationCountDesc(@Param("phonelines") Collection<Phoneline> phonelines);
}