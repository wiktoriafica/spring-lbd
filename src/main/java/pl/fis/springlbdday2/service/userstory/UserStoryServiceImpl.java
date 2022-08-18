package pl.fis.springlbdday2.service.userstory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.fis.springlbdday2.dto.attachment.AttachmentGetDto;
import pl.fis.springlbdday2.dto.attachment.AttachmentMapper;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryMapper;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.attachment.Attachment;
import pl.fis.springlbdday2.entity.enums.UserStoryStatus;
import pl.fis.springlbdday2.entity.sprint.Sprint;
import pl.fis.springlbdday2.entity.userstory.UserStory;
import pl.fis.springlbdday2.exception.InvalidDataException;
import pl.fis.springlbdday2.repository.attachment.AttachmentRepository;
import pl.fis.springlbdday2.repository.sprint.SprintRepository;
import pl.fis.springlbdday2.repository.userstory.UserStoryRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final SprintRepository sprintRepository;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentRepository attachmentRepository;

    @Override
    public void addUserStory(UserStoryPostDto userStoryPostDto, Long sprintId) {
        UserStory userStory = userStoryMapper.convertDtoToUserStory(userStoryPostDto);
        if (userStory.getStatus() == null)
            userStory.setStatus(UserStoryStatus.TO_DO);
        userStoryRepository.save(userStory);
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() ->
                new EntityNotFoundException("Sprint with id " + sprintId + " does not exists"));
        List<UserStory> userStories = sprintRepository.findUserStoriesBySprintId(sprintId);
        userStories.add(userStory);
        sprint.setUserStories(userStories);
        sprintRepository.save(sprint);
    }

    @Override
    public UserStory addUserStory(UserStoryPostDto userStoryPostDto) {
        if (userStoryPostDto.getName() == null && userStoryPostDto.getDescription() == null)
            throw new InvalidDataException("Violations of constraints in user stories");
        UserStory userStory = userStoryMapper.convertDtoToUserStory(userStoryPostDto);
        if (userStory.getStatus() == null)
            userStory.setStatus(UserStoryStatus.TO_DO);
        return userStoryRepository.save(userStory);
    }

    @Override
    public UserStoryGetDto getUserStoryById(Long id) {
        return userStoryMapper.getUserStoryGetDtoFromUserStory(userStoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " +
                "story with id " + id + " does not exists")));
    }

    @Override
    public UserStory getUserStoryEntityById(Long id) {
        return userStoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User " +
                "story with id " + id + " does not exists"));
    }

    @Override
    public List<UserStoryGetDto> getUsersStoriesBySprintId(Long id) {
        if (!sprintRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Sprint with id %d does not exists", id));
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
    @Transactional(readOnly = true, noRollbackFor = Exception.class)
    public UserStoryGetDto findUserStoryByName(String name) {
        return userStoryMapper
                .getUserStoryGetDtoFromUserStory
                        (userStoryRepository.findByName(name));
    }

    @Override
    public AttachmentGetDto getUserStoryAttachment(Long userStoryId, Integer attachmentNumber) {
        List<Attachment> attachments = getUserStoryById(userStoryId).getAttachments();
        if (!attachments.isEmpty() && attachments.size() >= attachmentNumber)
            return attachmentMapper.getDtoFromAttachment(attachments.get(attachmentNumber - 1));
        throw new InvalidDataException(String.format("User story does not have any attachments" +
                " or attachment with number %d does not exists", attachmentNumber));
    }

    @Override
    public void uploadUserStoryAttachment(Long userStoryId, MultipartFile file) {
        if (file == null)
            throw new InvalidDataException("No file provided");
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("User story with id %d does not exists", userStoryId)));
        List<Attachment> attachments = userStory.getAttachments();
        try {
            Attachment attachment = new Attachment();
            attachment.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
            attachment.setContentType(file.getContentType());
            attachment.setAttachment(file.getBytes());
            attachmentRepository.save(attachment);
            attachments.add(attachment);
            userStory.setAttachments(attachments);
            userStoryRepository.save(userStory);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deleteUserStoryById(Long id) {
        if (!userStoryRepository.existsById(id))
            throw new EntityNotFoundException(String.format("User story with id %d does not exists", id));
        userStoryRepository.deleteById(id);
    }

    @Transactional(rollbackFor = InvalidDataException.class)
    public void addUserStories() throws InvalidDataException {
        UserStoryPostDto userStory = new UserStoryPostDto();
        userStory.setName("User story");
        userStory.setStoryPoints(15);
        userStory.setDescription("This is another user story");
        userStory.setStatus(UserStoryStatus.DONE);

        UserStoryPostDto userStory1 = new UserStoryPostDto();
        userStory1.setName("User story1");
        userStory1.setStoryPoints(54);
        userStory1.setDescription("This is yet another user story");

        UserStoryPostDto userStory2 = new UserStoryPostDto();
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
            log.info("Creating user stories...");
            addUserStories();
        } catch (Exception exception) {
            log.info("Error occurred while adding user story to db");
        }
    }
}
