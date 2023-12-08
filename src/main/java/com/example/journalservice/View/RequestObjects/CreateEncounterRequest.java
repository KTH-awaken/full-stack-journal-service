package com.example.journalservice.View.RequestObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEncounterRequest {
    private String patientEmail;
    private String doctorEmail;
    private LocalDateTime date;
    private TimeVm time;
    private String title;
    private String description;
}
