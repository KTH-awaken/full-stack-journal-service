package com.example.journalservice.View.RequestObjects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConditionRequest {
    private String diagnosis;
    private String patientEmail;
    private String doctorEmail;
}
