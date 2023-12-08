package com.example.journalservice.View.ViewModels;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVm {
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
}
