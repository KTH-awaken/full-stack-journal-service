import com.example.journalservice.Core.EncounterMapper;
import com.example.journalservice.Core.EncounterService;
import com.example.journalservice.Core.WebClientService;
import com.example.journalservice.Entities.Encounter;
import com.example.journalservice.Repository.IEncounterRepo;
import com.example.journalservice.Repository.IObservationRepo;
import com.example.journalservice.View.ViewModels.AccountVm;
import com.example.journalservice.View.ViewModels.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JournalServiceTests {
    private IEncounterRepo encounterRepo;
    private IObservationRepo observationRepo;
    private WebClientService webClientService;
    private EncounterMapper mapper;
    private EncounterService encounterService;

    @BeforeEach
    void setUp() {
        encounterRepo = mock(IEncounterRepo.class);
        observationRepo = mock(IObservationRepo.class);
        webClientService = mock(WebClientService.class);
        mapper = mock(EncounterMapper.class);
        encounterService = new EncounterService(encounterRepo, observationRepo, webClientService, mapper);
    }

    @Test
    void getUserEncounters_ShouldReturnEncounters() {
        String username = "test@example.com";
        AccountVm mockUser = new AccountVm();
        mockUser.setUserType(UserType.PATIENT);
        List<Encounter> mockEncounters = new ArrayList<>();

        when(webClientService.fetchUserFromUserService(anyString(), anyString())).thenReturn(Mono.just(mockUser));
        when(encounterRepo.findByPatientEmail(username)).thenReturn(mockEncounters);

        List<Encounter> result = encounterService.getUserEncounters(username, "authHeader");

        assertEquals(mockEncounters, result);
        System.out.println("TEST FINISHED");
    }
}
