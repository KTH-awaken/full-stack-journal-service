package com.example.journalservice.View.ViewModels;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncounterVm {
    private long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private LocalDateTime timestamp;
    private List<ObservationVm> observations;
}
