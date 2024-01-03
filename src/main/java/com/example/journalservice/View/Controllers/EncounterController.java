package com.example.journalservice.View.Controllers;

import com.example.journalservice.Core.EncounterMapper;
import com.example.journalservice.Core.EncounterService;
import com.example.journalservice.Core.Security.TokenDecoder;
import com.example.journalservice.View.RequestObjects.CreateEncounterRequest;
import com.example.journalservice.View.RequestObjects.CreateObservationRequest;
import com.example.journalservice.View.ViewModels.EncounterDetailsVm;
import com.example.journalservice.View.ViewModels.EncounterVm;
import com.example.journalservice.View.ViewModels.ObservationVm;
import com.example.journalservice.View.ViewModels.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping
public class EncounterController {

    private final EncounterService encounterService;
    private final EncounterMapper mapper;

    @GetMapping("/encounter")
    public ResponseEntity<List<EncounterVm>> getUserEncounters(@RequestHeader("Authorization") String authorizationHeader) {
        String email = TokenDecoder.getEmailFromToken(authorizationHeader);
        return  ResponseEntity.ok(mapper.toEncounterVMs(encounterService.getUserEncounters(email,authorizationHeader)));
    }

    @GetMapping("/encounter/details/{encounterId}")
    public ResponseEntity<EncounterDetailsVm> getEncounterDetails(@PathVariable long encounterId, @RequestHeader("Authorization") String authorizationHeader) {
        return  ResponseEntity.ok(encounterService.getEncounterDetails(encounterId,authorizationHeader));

    }



    @GetMapping("/encounter/{patientEmail}")
    public ResponseEntity<List<EncounterVm>> getPatientEncounter(@PathVariable String patientEmail, @RequestHeader("Authorization") String authHeader){
        // DOCTOR AND EMPLOYEE ONLY
        boolean isDoctor = TokenDecoder.getRoleFromToken(authHeader).equals(UserType.DOCTOR.name());
        boolean isEmployee = TokenDecoder.getRoleFromToken(authHeader).equals(UserType.EMPLOYEE.name());
        if(!isDoctor && !isEmployee)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(mapper.toEncounterVMs(encounterService.getPatientEncounters(patientEmail)));

    }

    @PostMapping("/encounter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EncounterVm> createEncounter(@RequestBody CreateEncounterRequest request, @RequestHeader("Authorization") String authHeader){
        // DOCTOR AND EMPLOYEE ONLY
        boolean isDoctor = TokenDecoder.getRoleFromToken(authHeader).equals(UserType.DOCTOR.name());
        boolean isEmployee = TokenDecoder.getRoleFromToken(authHeader).equals(UserType.EMPLOYEE.name());
        if(!isDoctor && !isEmployee)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(mapper.toEncounterVM(encounterService.createEncounter(request)));
    }

    @PostMapping("/encounter/observation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ObservationVm> addObservation(@RequestBody CreateObservationRequest request, @RequestHeader("Authorization") String authHeader){
        // DOCTOR AND EMPLOYEE ONLY
        boolean isDoctor = TokenDecoder.getRoleFromToken(authHeader).equals(UserType.DOCTOR.name());
        boolean isEmployee = TokenDecoder.getRoleFromToken(authHeader).equals(UserType.EMPLOYEE.name());
        if(!isDoctor && !isEmployee)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(mapper.toObservationVM(encounterService.addObservation( request)));
    }


}
