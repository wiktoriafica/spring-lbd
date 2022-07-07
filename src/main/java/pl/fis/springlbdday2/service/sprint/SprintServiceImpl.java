package pl.fis.springlbdday2.service.sprint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.sprint.SprintRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);
    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override

    public void addSprint(Sprint sprint) throws InvalidDataException {
        if (sprint.getStartDate() != null && sprint.getEndDate() != null &&
                sprint.getStatus() != null && sprint.getName() != null &&
                sprint.getEndDate().isAfter(sprint.getStartDate()))
            sprintRepository.save(sprint);
        else throw new InvalidDataException("Violations of constraints in sprints");
    }

    @Override
    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity " +
                "with id " + id + " does not exists"));
    }

    //TODO: ex. 7, transaction is not rolled back and I don't know how to make it work
    @Transactional(rollbackFor = InvalidDataException.class)
    public void addSprints() throws InvalidDataException {
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
        addSprint(sprint);
        addSprint(sprint1);
        addSprint(sprint2);
    }

    @PostConstruct
    public void postConstruct() {
        try {
            LOGGER.info("Creating sprints...");
            addSprints();
        } catch(Exception exception) {
            LOGGER.info("Error occurred while adding sprint to db");
        }
    }
}
