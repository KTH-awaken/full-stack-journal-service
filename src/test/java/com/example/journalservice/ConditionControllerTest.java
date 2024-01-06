package com.example.journalservice;

import com.example.journalservice.Core.ConditionService;
import com.example.journalservice.Entities.Condition;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.example.journalservice.View.RequestObjects.CreateConditionRequest;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = JournalServiceApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)

class ConditionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConditionService conditionService;

    @Test
    @WithMockUser(username = "hamada", roles = {"DOCTOR"})
    void getPatientConditionsTest() throws Exception {
        // Create mock data for the service layer
        Condition condition1 = Condition.builder()
                .id(1)
                .patientEmail("patient1@example.com")
                .doctorEmail("doctor1@example.com")
                .diagnosis("Diagnosis 1")
                .timestamp(LocalDateTime.now())
                .build();
        Condition condition2 = Condition.builder()
                .id(2)
                .patientEmail("patient2@example.com")
                .doctorEmail("doctor2@example.com")
                .diagnosis("Diagnosis 2")
                .timestamp(LocalDateTime.now())
                .build();
        List<Condition> mockConditions = Arrays.asList(condition1, condition2);

        // Mock the service method
        when(conditionService.getPatientConditions(anyString())).thenReturn(mockConditions);

        // Perform the MockMvc request and assert
        mockMvc.perform(get("/conditions/{patientEmail}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(condition1.getId()))
                .andExpect(jsonPath("$[0].diagnosis").value(condition1.getDiagnosis()))
                .andExpect(jsonPath("$[1].id").value(condition2.getId()))
                .andExpect(jsonPath("$[1].diagnosis").value(condition2.getDiagnosis()));
    }




}
