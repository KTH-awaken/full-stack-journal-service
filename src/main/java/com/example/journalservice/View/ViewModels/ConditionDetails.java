package com.example.journalservice.View.ViewModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionDetails {
    private long id;
    private LocalDateTime timestamp;
    private String diagnosis;
    private AccountVm doctor;
    private AccountVm patient;
}
