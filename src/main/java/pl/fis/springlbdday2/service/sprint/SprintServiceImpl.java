package pl.fis.springlbdday2.service.sprint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.dto.sprint.SprintMapper;
import pl.fis.springlbdday2.dto.sprint.SprintPostDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.event.UserStoryCreatedEvent;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.sprint.SprintRepository;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private final UserStoryService userStoryService;
    private final SprintMapper sprintMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);
    public SprintServiceImpl(SprintRepository sprintRepository,
                             UserStoryService userStoryService,
                             SprintMapper sprintMapper) {
        this.sprintRepository = sprintRepository;
        this.userStoryService = userStoryService;
        this.sprintMapper = sprintMapper;
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
    @Transactional(rollbackFor = InvalidDataException.class)
    public void addSprintWithUserStories() throws InvalidDataException  {
        UserStory userStory1 = new UserStory();
        userStory1.setStoryPoints(12);
        userStory1.setName("Story 1");
        userStory1.setDescription("This is user story 1");
        userStory1.setStatus(UserStoryStatus.IN_PROGRESS);

        UserStory userStory2 = new UserStory();
        userStory2.setStoryPoints(23);
        userStory2.setName("Story 2");
        userStory2.setDescription("This is user story 2");
        userStory2.setStatus(UserStoryStatus.TO_DO);

        sprintRepository.save(new Sprint("Another sprint nr 1",
                LocalDate.now(), LocalDate.now(), "Sprint goal",
                SprintStatus.IN_PROGRESS, List.of(userStoryService.addUserStory(userStory1),
                userStoryService.addUserStory(userStory2))));
    }

    @Override
    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity " +
                "with id " + id + " does not exists"));
    }

    @Override
    @Transactional
    public List<UserStory> getUsersStoriesBySprintId(Long id) {
        return sprintRepository.findUserStoriesBySprintId(id);
    }

    @Override
    public List<Sprint> getSprintsFromGivenTime(LocalDate startDate, LocalDate endDate) {
        return sprintRepository.findByStartDateBetween(startDate, endDate);
    }

    @Override
    public List<SprintGetDto> getSprintsFromGivenTime(String startDate, String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            return sprintRepository.findByStartDateBetween(start, end)
                    .stream()
                    .map(sprintMapper::getDtoFromSprint)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException exception) { return null; }
    }

    @Override
    public Integer getStoryPointsFromSprint(Long id) {
        return sprintRepository.getStoryPointsFromSprint(id);
    }

    @Override
    public void deleteSprintById(Long id) {
        sprintRepository.deleteById(id);
    }

    @Override
    public Page<Sprint> getPaginatedAndSortedSprints(Pageable page) {
        return sprintRepository.findAll(page);
    }

    @Override
    public LocalDate getLastStartDate() {
        return sprintRepository.getLastStartDate();
    }

    @Override
    public List<SprintGetDto> getSprints(boolean tasks) {
        return sprintRepository
                .findAll()
                .stream()
                .map(tasks ? sprintMapper::getDtoFromSprintWithUserStories : sprintMapper::getDtoFromSprint)
                .collect(Collectors.toList());
    }

    @Override
    public void updateSprint(Long id, SprintPostDto sprintPostDto) {
        Sprint sprint = sprintRepository.findById(id)
                .map(foundSprint -> sprintMapper.updateSprintFromSprintPostDto(sprintPostDto, foundSprint))
                .orElseThrow(() -> new EntityNotFoundException("Entity " +
                        "with id " + id + " does not exists"));
        sprintRepository.save(sprint);
    }

    @Override
    @EventListener
    public void userStoryCreatedEventHandler(UserStoryCreatedEvent userStoryCreatedEvent) {
        Sprint latestSprint = sprintRepository.getNewestPendingSprint();
        UserStory userStory = userStoryService.getUserStoryById(userStoryCreatedEvent.getUserCreatedStoryId());
        List<UserStory> userStories = sprintRepository.findUserStoriesBySprintId(latestSprint.getId());
        userStories.add(userStory);
        latestSprint.setUserStories(userStories);
        sprintRepository.save(latestSprint);
    }

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
            LOGGER.info("Creating sprint with user stories...");
            addSprintWithUserStories();
            LOGGER.info("Creating sprints...");
            addSprints();
        } catch(Exception exception) {
            LOGGER.info("Error occurred while adding sprint to db");
        }

    }
}
