package com.example.journalservice.Repository;



import com.example.journalservice.Entities.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConditionRepo extends JpaRepository<Condition, Long> {
    List<Condition> findByPatientEmail(String email);
}
