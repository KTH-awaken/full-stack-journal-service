package com.example.journalservice.View.Controllers;

import com.example.journalservice.Core.EncounterMapper;
import com.example.journalservice.Core.EncounterService;
import com.example.journalservice.Core.Security.TokenDecoder;
import com.example.journalservice.View.RequestObjects.CreateEncounterRequest;
import com.example.journalservice.View.RequestObjects.CreateObservationRequest;
import com.example.journalservice.View.ViewModels.EncounterDetailsVm;
import com.example.journalservice.View.ViewModels.EncounterVm;
import com.example.journalservice.View.ViewModels.ObservationVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/encounter")
public class EncounterController {

    private final EncounterService encounterService;
    private final EncounterMapper mapper;

    @GetMapping
    public ResponseEntity<List<EncounterVm>> getUserEncounters(@RequestHeader("Authorization") String authorizationHeader) {
        String email = TokenDecoder.getEmailFromToken(authorizationHeader);

        return  ResponseEntity.ok(mapper.toEncounterVMs(encounterService.getUserEncounters(email,authorizationHeader)));
    }

    @GetMapping("/details/{encounterId}")
    public ResponseEntity<EncounterDetailsVm> getEncounterDetails(@PathVariable long encounterId, @RequestHeader("Authorization") String authorizationHeader) {
        return  ResponseEntity.ok(encounterService.getEncounterDetails(encounterId,authorizationHeader));


    }



    @GetMapping("/{patientEmail}")
    public ResponseEntity<List<EncounterVm>> getPatientEncounter(@PathVariable String patientEmail){
//        throw new Exception("This is custom message");
        return ResponseEntity.ok(mapper.toEncounterVMs(encounterService.getPatientEncounters(patientEmail)));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EncounterVm> createEncounter(@RequestBody CreateEncounterRequest request){
        return ResponseEntity.ok(mapper.toEncounterVM(encounterService.createEncounter(request)));
    }

    @PostMapping("/observation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ObservationVm> addObservation(@RequestBody CreateObservationRequest request){
        return ResponseEntity.ok(mapper.toObservationVM(encounterService.addObservation( request)));
    }


}
