package com.example.journalservice.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@Table(name = "encounter")
@AllArgsConstructor
@NoArgsConstructor
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String patientEmail;
    private String doctorEmail;
    private LocalDateTime date;
    private LocalDateTime timestamp;
    private String title;
    private String description;

    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL)
    private List<Observation> observations;

}
