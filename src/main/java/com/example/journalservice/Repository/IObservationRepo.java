package com.example.journalservice.Repository;


import com.example.journalservice.Entities.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IObservationRepo extends JpaRepository<Observation,Long> {
}
