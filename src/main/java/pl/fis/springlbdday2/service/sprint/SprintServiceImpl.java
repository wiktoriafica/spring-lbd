package pl.fis.springlbdday2.service.sprint;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.sprint.SprintRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;

    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    @Transactional(rollbackFor = InvalidDataException.class)
    public void addSprint(Sprint sprint) {
        if (sprint.getStartDate() != null && sprint.getEndDate() != null &&
                sprint.getStatus() != null && sprint.getName() != null &&
                sprint.getEndDate().isAfter(sprint.getStartDate()))
            sprintRepository.save(sprint);
        else throw new InvalidDataException("Violations of constraints in sprints");
    }

    @PostConstruct
    public void addSprints() {
        Sprint sprint = new Sprint();
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(14));
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setName("Another sprint");
        Sprint sprint1 = new Sprint();
        sprint1.setStartDate(LocalDate.now());
        sprint1.setEndDate(LocalDate.now().minusDays(14));
        Sprint sprint2 = new Sprint();
        sprint2.setEndDate(LocalDate.now().minusDays(14));
        sprint2.setName("And another sprint");
        try {
            addSprint(sprint);
            addSprint(sprint1);
            addSprint(sprint2);
        } catch(InvalidDataException exception) {}
    }
}
