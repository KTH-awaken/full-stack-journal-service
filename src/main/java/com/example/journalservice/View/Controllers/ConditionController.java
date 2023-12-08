package com.example.journalservice.View.Controllers;

import com.example.journalservice.Core.ConditionService;
import com.example.journalservice.Core.Mapper;
import com.example.journalservice.Entities.Condition;
import com.example.journalservice.View.RequestObjects.CreateConditionRequest;
import com.example.journalservice.View.ViewModels.ConditionDetails;
import com.example.journalservice.View.ViewModels.ConditionVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ConditionController {
    private final ConditionService conditionService;


    @GetMapping("/conditions/{patientEmail}")
    public ResponseEntity<List<ConditionVm>> getPatientCondition(@PathVariable String patientEmail){
        List<Condition> conditions = conditionService.getPatientConditions(patientEmail);
        List<ConditionVm> conditionVms = Mapper.toConditionVms(conditions);
        return ResponseEntity.ok(conditionVms);
    }

    @GetMapping("/condition/{conditionId}")
    public ResponseEntity<ConditionDetails> getConditionDetails(@PathVariable long conditionId, @RequestHeader("Authorization") String authHeader){
        return ResponseEntity.ok(conditionService.getConditionDetails(conditionId, authHeader));
    }

    @PostMapping("/condition")
    public ResponseEntity<ConditionVm> createPatientCondition(@RequestBody CreateConditionRequest request){
        Condition condition = conditionService.createCondition(request);
        return ResponseEntity.ok(Mapper.toConditionVm(condition));
    }
}
