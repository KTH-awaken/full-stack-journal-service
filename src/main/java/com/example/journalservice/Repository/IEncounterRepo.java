package com.example.journalservice.Repository;


import com.example.journalservice.Entities.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEncounterRepo extends JpaRepository<Encounter,Long> {
    List<Encounter> findByPatientEmail(String email);
    List<Encounter> findByDoctorEmail(String Email);


}
