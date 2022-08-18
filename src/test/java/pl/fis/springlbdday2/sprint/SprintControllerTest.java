package pl.fis.springlbdday2.sprint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.fis.springlbdday2.controller.sprint.SprintController;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.service.sprint.SprintService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SprintControllerTest {

    @Autowired
    private SprintController sprintController;
    @Autowired
    private SprintService sprintService;

    @Test
    public void givenSprint_whenUpdateStatus_thenSuccess() {
        sprintController.updateSprintStatus(5L, SprintStatus.FINISHED);

        SprintGetDto sprint = sprintService.getSprintById(5L);

        assertEquals(SprintStatus.FINISHED, sprint.getStatus());
    }
}
