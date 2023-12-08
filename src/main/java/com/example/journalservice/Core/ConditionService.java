package com.example.journalservice.Core;

import com.example.journalservice.Entities.Condition;
import com.example.journalservice.Repository.IConditionRepo;
import com.example.journalservice.View.RequestObjects.CreateConditionRequest;
import com.example.journalservice.View.ViewModels.AccountVm;
import com.example.journalservice.View.ViewModels.ConditionDetails;
import com.example.journalservice.View.ViewModels.ConditionVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionService {
    private final IConditionRepo conditionRepo;
    private final WebClientService webClientService;



    public List<Condition> getPatientConditions(String patientEmail){
        return this.conditionRepo.findByPatientEmail(patientEmail);
    }

    public Condition createCondition(CreateConditionRequest request){
        LocalDateTime timestamp = LocalDateTime.now();
        Condition conditionToSave = Condition.builder()
                .patientEmail(request.getPatientEmail())
                .doctorEmail(request.getDoctorEmail())
                .timestamp(timestamp)
                .diagnosis(request.getDiagnosis())
                .build();
        return conditionRepo.save(conditionToSave);
    }

    public ConditionDetails getConditionDetails(long id, String authHeader){
        Condition condition =  conditionRepo.getReferenceById(id);
        AccountVm doctor = getUserByEmail(condition.getDoctorEmail(), authHeader);
        AccountVm patient = getUserByEmail(condition.getPatientEmail(), authHeader);

        return ConditionDetails.builder()
                .doctor(doctor)
                .patient(patient)
                .id(condition.getId())
                .diagnosis(condition.getDiagnosis())
                .timestamp(condition.getTimestamp())
                .build();
    }


    private AccountVm getUserByEmail(String email, String authHeader){
        AccountVm user = webClientService.fetchUserFromUserService("http://localhost:8081/user/" + email, authHeader).block();
        return user;
    }

}
