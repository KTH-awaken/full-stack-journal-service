package com.example.journalservice.Core;

import com.example.journalservice.Core.Security.TokenDecoder;
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

    public Condition createCondition(CreateConditionRequest request, String authHeader){
        String doctorEmail = TokenDecoder.getEmailFromToken(authHeader);
        LocalDateTime timestamp = LocalDateTime.now();
        Condition conditionToSave = Condition.builder()
                .patientEmail(request.getPatientEmail())
                .doctorEmail(doctorEmail)
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
        AccountVm user = webClientService.fetchUserFromUserService("https://health-user-service.app.cloud.cbh.kth.se/user/" + email, authHeader).block();
        return user;
    }

}
