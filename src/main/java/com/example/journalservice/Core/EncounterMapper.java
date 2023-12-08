package com.example.journalservice.Core;


import com.example.journalservice.Entities.Encounter;
import com.example.journalservice.Entities.Observation;
import com.example.journalservice.View.ViewModels.EncounterVm;
import com.example.journalservice.View.ViewModels.ObservationVm;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EncounterMapper {

    ObservationVm toObservationVM (Observation observation);
    List<ObservationVm> toObservationVMs(List<Observation> observations);



    EncounterVm toEncounterVM(Encounter encounter);
    List<EncounterVm> toEncounterVMs(List<Encounter> encounters);


}
