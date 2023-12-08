package com.example.journalservice.View.ViewModels;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConditionVm {
    private long id;
    private LocalDateTime timestamp;
    private String diagnosis;
}
