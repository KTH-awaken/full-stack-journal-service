package com.example.journalservice.View.Controllers;

import com.example.journalservice.Core.ConditionService;
import com.example.journalservice.Core.Mapper;
import com.example.journalservice.Core.Security.TokenDecoder;
import com.example.journalservice.Entities.Condition;
import com.example.journalservice.View.RequestObjects.CreateConditionRequest;
import com.example.journalservice.View.ViewModels.ConditionDetails;
import com.example.journalservice.View.ViewModels.ConditionVm;
import com.example.journalservice.View.ViewModels.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ConditionController {
    private final ConditionService conditionService;


    @GetMapping("/conditions/{patientEmail}")
    public ResponseEntity<List<ConditionVm>> getPatientConditions(@PathVariable String patientEmail){
        List<Condition> conditions = conditionService.getPatientConditions(patientEmail);
        List<ConditionVm> conditionVms = Mapper.toConditionVms(conditions);
        return ResponseEntity.ok(conditionVms);
    }

    @GetMapping("/conditions")
    public ResponseEntity<List<ConditionVm>> getUserConditions(@RequestHeader("Authorization") String authHeader){

        List<Condition> conditions = conditionService.getPatientConditions(TokenDecoder.getEmailFromToken(authHeader));
        List<ConditionVm> conditionVms = Mapper.toConditionVms(conditions);
        return ResponseEntity.ok(conditionVms);
    }


    @GetMapping("/condition/{conditionId}")
    public ResponseEntity<ConditionDetails> getConditionDetails(@PathVariable long conditionId, @RequestHeader("Authorization") String authHeader){
        ConditionDetails details = conditionService.getConditionDetails(conditionId, authHeader);
        return ResponseEntity.ok(details);
    }


    @PostMapping("/condition")
    public ResponseEntity<ConditionVm> createPatientCondition(@RequestBody CreateConditionRequest request, @RequestHeader("Authorization") String authHeader){
        Condition condition = conditionService.createCondition(request, authHeader);
        return ResponseEntity.ok(Mapper.toConditionVm(condition));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("ok");
    }

}
