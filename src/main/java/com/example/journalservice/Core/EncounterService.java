package com.example.journalservice.Core;

import com.example.journalservice.Entities.Encounter;
import com.example.journalservice.Entities.Observation;
import com.example.journalservice.Repository.IEncounterRepo;
import com.example.journalservice.Repository.IObservationRepo;
import com.example.journalservice.View.ViewModels.AccountVm;
import com.example.journalservice.View.RequestObjects.CreateEncounterRequest;
import com.example.journalservice.View.RequestObjects.CreateObservationRequest;
import com.example.journalservice.View.ViewModels.EncounterDetailsVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EncounterService {
    private final IEncounterRepo encounterRepo;
    private final IObservationRepo observationRepo;
    private final WebClientService webClientService;
    private final EncounterMapper mapper;

    public List<Encounter> getUserEncounters(String username, String authHeader){
        AccountVm user = getUserByEmail(username,authHeader);


        List<Encounter> encounters;
        switch (user.getUserType()){
            case PATIENT -> encounters = encounterRepo.findByPatientEmail(username);
            case DOCTOR -> encounters = encounterRepo.findByDoctorEmail(username);
            default -> encounters = new ArrayList<>();
        }
        return encounters;
    }


    public Encounter createEncounter(CreateEncounterRequest request){
        int year = request.getDate().getYear();
        int month = request.getDate().getMonthValue();
        int day = request.getDate().getDayOfMonth();
        int hour = request.getTime().getHour();
        int minute = request.getTime().getMinute();


        LocalDateTime date = LocalDateTime.of(year,month,day,hour,minute);
        Encounter encounter = Encounter
                .builder()
                .title(request.getTitle())
                .timestamp(LocalDateTime.now())
                .description(request.getDescription())
                .patientEmail(request.getPatientEmail())
                .doctorEmail(request.getDoctorEmail())
                .date(date)
                .observations(new ArrayList<>())
                .build();

        return encounterRepo.save(encounter);

    }

    public EncounterDetailsVm getEncounterDetails(long id, String authHeader){
        Encounter encounter = encounterRepo.getReferenceById(id);
        AccountVm doctor = getUserByEmail(encounter.getDoctorEmail(), authHeader);
        AccountVm patient = getUserByEmail(encounter.getPatientEmail(), authHeader);

        return EncounterDetailsVm.builder()
                .doctor(doctor)
                .patient(patient)
                .title(encounter.getTitle())
                .date(encounter.getDate())
                .description(encounter.getDescription())
                .id(encounter.getId())
                .observations(mapper.toObservationVMs(encounter.getObservations()))
                .timestamp(encounter.getTimestamp())
                .build();
    }

    public List<Encounter> getPatientEncounters(String email){
        List<Encounter> encounters = encounterRepo.findByPatientEmail(email);
        return encounters;
    }

    public Observation addObservation(CreateObservationRequest request){
        Encounter enc = encounterRepo.getReferenceById(request.getEncounterId());
        Observation obsToSave = Observation.builder().encounter(enc).description(request.getContent()).date(LocalDateTime.now()).build();
        Observation observation = observationRepo.save(obsToSave);
        return observation;

    }


    private AccountVm getUserByEmail(String email, String authHeader){
        AccountVm user = webClientService.fetchUserFromUserService("http://localhost:8081/user/" + email, authHeader).block();
        return user;
    }
}
