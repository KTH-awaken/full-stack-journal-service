package com.example.journalservice.View.RequestObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateObservationRequest {
    private String content;
    private long encounterId;
}
