package pl.fis.springlbdday2.service.sprint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.dto.sprint.SprintMapper;
import pl.fis.springlbdday2.dto.sprint.SprintPostDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryMapper;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private final UserStoryService userStoryService;
    private final SprintMapper sprintMapper;
    private final UserStoryMapper userStoryMapper;

    private void validateSprint(SprintPostDto sprint) {
        if (sprint.getStartDate() == null || sprint.getEndDate() == null ||
                sprint.getStatus() == null || sprint.getName() == null)
            throw new InvalidDataException("No necessary sprint data provided");
        if (!sprint.getEndDate().isAfter(sprint.getStartDate()))
            throw new InvalidDataException("End date cannot be earlier than start date");
        if (Arrays.stream(SprintStatus.values()).noneMatch(sprint.getStatus()::equals))
            throw new InvalidDataException("Wrong status provided");
    }

    @Override
    @Transactional(rollbackFor = InvalidDataException.class)
    public Sprint addSprint(SprintPostDto sprint) throws InvalidDataException {
        validateSprint(sprint);
        Sprint sprintToAdd = sprintMapper.sprintPostDtoToSprint(sprint);
        return sprintRepository.save(sprintToAdd);
    }

    @Override
    @Transactional(rollbackFor = InvalidDataException.class)
    public void addSprintWithUserStories() throws InvalidDataException {
        UserStoryPostDto userStory1 = new UserStoryPostDto();
        userStory1.setStoryPoints(12);
        userStory1.setName("Story 1");
        userStory1.setDescription("This is user story 1");
        userStory1.setStatus(UserStoryStatus.IN_PROGRESS);

        UserStoryPostDto userStory2 = new UserStoryPostDto();
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
    @Transactional(readOnly = true, noRollbackFor = Exception.class)
    public SprintGetDto getSprintById(Long id) {
        return sprintMapper.getDtoFromSprintWithUserStories(sprintRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity " +
                "with id " + id + " does not exists")));
    }

    @Override
    @Transactional
    public List<UserStoryGetDto> getUsersStoriesBySprintId(Long id) {
        return sprintRepository.findUserStoriesBySprintId(id)
                .stream()
                .map(userStoryMapper::getUserStoryGetDtoFromUserStory)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = Exception.class)
    public List<SprintGetDto> getSprintsFromGivenTime(LocalDate startDate, LocalDate endDate) {
        return sprintRepository.findByStartDateBetween(startDate, endDate)
                .stream()
                .map(sprintMapper::getDtoFromSprintWithUserStories)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = Exception.class)
    public List<SprintGetDto> getSprintsFromGivenTime(String startDate, String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            return sprintRepository.findByStartDateBetween(start, end)
                    .stream()
                    .map(sprintMapper::getDtoFromSprint)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    @Override
    public Integer getStoryPointsFromSprint(Long id) {
        if (!sprintRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Sprint with id %d does not exists", id));
        return sprintRepository.getStoryPointsFromSprint(id);
    }

    @Override
    public void deleteSprintById(Long id) {
        if (!sprintRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Sprint with id %d does not exists", id));
        sprintRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = Exception.class)
    public Page<SprintGetDto> getPaginatedAndSortedSprints(Pageable page) {
        return new PageImpl<>(sprintRepository
                .findAll(page)
                .stream()
                .map(sprintMapper::getDtoFromSprintWithUserStories)
                .collect(Collectors.toList()));
    }

    @Override
    public LocalDate getLastStartDate() {
        return sprintRepository.getLastStartDate();
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = Exception.class)
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
                .orElseThrow(() -> new EntityNotFoundException("Sprint " +
                        "with id " + id + " does not exists"));
        sprintRepository.save(sprint);
    }

    @Override
    @EventListener
    public void userStoryCreatedEventHandler(UserStoryCreatedEvent userStoryCreatedEvent) {
        Sprint latestSprint = sprintRepository.getNewestPendingSprint();
        UserStory userStory = userStoryService.getUserStoryEntityById(userStoryCreatedEvent.getUserCreatedStoryId());
        List<UserStory> userStories = sprintRepository.findUserStoriesBySprintId(latestSprint.getId());
        userStories.add(userStory);
        latestSprint.setUserStories(userStories);
        sprintRepository.save(latestSprint);
    }

    @Transactional(rollbackFor = InvalidDataException.class)
    public void addSprints() throws InvalidDataException {
        SprintPostDto sprint = new SprintPostDto();
        sprint.setStartDate(LocalDate.now());
        sprint.setEndDate(LocalDate.now().plusDays(14));
        sprint.setStatus(SprintStatus.PENDING);
        sprint.setName("Another sprint");
        SprintPostDto sprint1 = new SprintPostDto();
        sprint1.setStartDate(LocalDate.now());
        sprint1.setEndDate(LocalDate.now().minusDays(14));
        SprintPostDto sprint2 = new SprintPostDto();
        sprint2.setEndDate(LocalDate.now().minusDays(14));
        sprint2.setName("And another sprint");
        addSprint(sprint);
        addSprint(sprint1);
        addSprint(sprint2);
    }


    @PostConstruct
    public void postConstruct() {
        try {
            log.info("Creating sprint with user stories...");
            addSprintWithUserStories();
            log.info("Creating sprints...");
            addSprints();
        } catch (Exception exception) {
            log.info("Error occurred while adding sprint to db");
        }
    }
}
