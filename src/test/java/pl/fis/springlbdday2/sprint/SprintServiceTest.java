package pl.fis.springlbdday2.sprint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.service.sprint.SprintService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@SpringBootTest
public class SprintServiceTest {
    @Autowired
    private SprintService sprintService;

    @Test
    public void givenCorrectSprint_whenAddingToDatabase_thenSuccess() {
        Sprint sprint = new Sprint();
        sprint.setName("Sprint 1");
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(21));

        sprintService.addSprint(sprint);
        Sprint addedSprint = sprintService.getSprintById(sprint.getId());

        assertThat(addedSprint.getId()).isEqualTo(sprint.getId());
        assertThat(addedSprint.getName()).isEqualTo(sprint.getName());
        assertThat(addedSprint.getStartDate()).isEqualTo(sprint.getStartDate());
        assertThat(addedSprint.getEndDate()).isEqualTo(sprint.getEndDate());
        assertThat(addedSprint.getStatus()).isEqualTo(sprint.getStatus());
    }

    @Test
    public void givenIncorrectSprint_whenAddingToDatabase_thenThrow() {
        Sprint sprint = new Sprint();
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().minusDays(21));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            sprintService.addSprint(sprint);
        });

        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
        assertNull(sprint.getId());
    }
}
