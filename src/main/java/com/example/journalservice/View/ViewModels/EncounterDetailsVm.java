package com.example.journalservice.View.ViewModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncounterDetailsVm {
    private long id;
    private AccountVm doctor;
    private AccountVm patient;
    private String title;
    private String description;
    private LocalDateTime date;
    private LocalDateTime timestamp;
    private List<ObservationVm> observations;
}
