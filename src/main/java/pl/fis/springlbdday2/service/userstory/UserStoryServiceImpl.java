package pl.fis.springlbdday2.service.userstory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryMapper;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.sprint.SprintRepository;
import pl.fis.springlbdday2.repository.userstory.UserStoryRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStoryServiceImpl implements UserStoryService{
    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final SprintRepository sprintRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserStoryServiceImpl.class);

    public UserStoryServiceImpl(UserStoryRepository userStoryRepository,
                                UserStoryMapper userStoryMapper,
                                SprintRepository sprintRepository) {
        this.userStoryRepository = userStoryRepository;
        this.userStoryMapper = userStoryMapper;
        this.sprintRepository = sprintRepository;
    }

    @Override
    public UserStory addUserStory(UserStory userStory) throws InvalidDataException {
        if(userStory.getStatus() == null)
            userStory.setStatus(UserStoryStatus.TO_DO);
        if(userStory.getName() != null && userStory.getDescription() != null) {
            userStoryRepository.save(userStory);
            return userStory;
        }

        else throw new InvalidDataException("Violations of constraints in user stories");
    }

    @Override
    public void addUserStory(UserStoryPostDto userStoryPostDto, Long sprintId) {
        UserStory userStory = userStoryMapper.convertDtoToUserStory(userStoryPostDto);
        userStoryRepository.save(userStory);
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() ->
                new EntityNotFoundException("Sprint with id " + sprintId + " does not exists"));
        List<UserStory> userStories = sprintRepository.findUserStoriesBySprintId(sprintId);
        userStories.add(userStory);
        sprint.setUserStories(userStories);
        sprintRepository.save(sprint);
    }

    @Override
    public Long addUserStory(UserStoryPostDto userStoryPostDto) {
        UserStory userStory = userStoryMapper.convertDtoToUserStory(userStoryPostDto);
        userStoryRepository.save(userStory);
        return userStory.getId();
    }

    @Override
    public UserStory getUserStoryById(Long id) {
        return userStoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " +
                "story with id " + id + " does not exists"));
    }

    @Override
    public List<UserStoryGetDto> getUsersStoriesBySprintId(Long id) {
        return userStoryRepository.getUserStoriesBySprintId(id)
                .stream()
                .map(userStoryMapper::getUserStoryGetDtoFromUserStory)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoryGetDto> getUserStories() {
        return userStoryRepository.findAll()
                .stream()
                .map(userStoryMapper::getUserStoryGetDtoFromUserStory)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoryGetDto> getPaginatedAndSortedUserStories(Pageable page) {
        return userStoryRepository
                .findAll(page)
                .stream()
                .map(userStoryMapper::getUserStoryGetDtoFromUserStory)
                .collect(Collectors.toList());
    }

    @Override
    public UserStoryGetDto findUserStoryByName(String name) {
        return userStoryMapper
                .getUserStoryGetDtoFromUserStory
                        (userStoryRepository.findByName(name));
    }

    @Override
    public void deleteUserStoryById(Long id) {
        userStoryRepository.deleteById(id);
    }

    @Transactional(rollbackFor = InvalidDataException.class)
    public void addUserStories() throws InvalidDataException {
        UserStory userStory = new UserStory();
        userStory.setName("User story");
        userStory.setStoryPoints(15);
        userStory.setDescription("This is another user story");
        userStory.setStatus(UserStoryStatus.DONE);

        UserStory userStory1 = new UserStory();
        userStory1.setName("User story1");
        userStory1.setStoryPoints(54);
        userStory1.setDescription("This is yet another user story");

        UserStory userStory2 = new UserStory();
        userStory2.setName("User story2");
        userStory2.setDescription("This is another another user story");
        userStory2.setStatus(UserStoryStatus.IN_PROGRESS);
        addUserStory(userStory);
        addUserStory(userStory1);
        addUserStory(userStory2);
    }

    @PostConstruct
    public void postConstruct() {
        try {
            LOGGER.info("Creating user stories...");
            addUserStories();
        } catch(Exception exception) {
            LOGGER.info("Error occurred while adding user story to db");
        }
    }
}
