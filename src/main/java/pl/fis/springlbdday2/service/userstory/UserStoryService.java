package pl.fis.springlbdday2.service.userstory;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.fis.springlbdday2.dto.attachment.AttachmentGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.entity.userstory.UserStory;

import java.util.List;

public interface UserStoryService {
    void addUserStory(UserStoryPostDto userStoryPostDto, Long sprintId);

    UserStory addUserStory(UserStoryPostDto userStoryPostDto);

    UserStoryGetDto getUserStoryById(Long id);

    UserStory getUserStoryEntityById(Long id);

    void deleteUserStoryById(Long id);

    List<UserStoryGetDto> getUsersStoriesBySprintId(Long id);

    List<UserStoryGetDto> getUserStories();

    List<UserStoryGetDto> getPaginatedAndSortedUserStories(Pageable page);

    UserStoryGetDto findUserStoryByName(String name);

    AttachmentGetDto getUserStoryAttachment(Long userStoryId, Integer attachmentNumber);

    void uploadUserStoryAttachment(Long userStoryId, MultipartFile file);
}
