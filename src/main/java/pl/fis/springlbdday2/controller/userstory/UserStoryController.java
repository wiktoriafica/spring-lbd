package pl.fis.springlbdday2.controller.userstory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.fis.springlbdday2.dto.attachment.AttachmentGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.event.UserStoryCreatedEvent;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/userstories")
@RequiredArgsConstructor
public class UserStoryController {
    private final UserStoryService userStoryService;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<UserStoryGetDto>> getUserStories(
            @RequestParam(required = false) Long sprintId) {
        return ResponseEntity.ok().body(sprintId != null ? userStoryService.getUsersStoriesBySprintId(sprintId) :
                userStoryService.getUserStories());
    }

    @GetMapping(path = "/{id}/description")
    public ResponseEntity<String> getUserStoryDescription(@PathVariable Long id) {
        return ResponseEntity.ok().body(userStoryService.getUserStoryById(id).getDescription());
    }

    @GetMapping(path = "/{userStoryId}/attachments/{attachmentNumber}")
    public ResponseEntity<byte[]> downloadUserStoryAttachment(@PathVariable Long userStoryId, @PathVariable Integer attachmentNumber){
        AttachmentGetDto attachment = userStoryService.getUserStoryAttachment(userStoryId, attachmentNumber);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getFileName())
                .contentType(MediaType.valueOf(attachment.getContentType()))
                .body(attachment.getAttachment());
    }

    @PostMapping(path = "/{userStoryId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadUserStoryAttachment(@PathVariable Long userStoryId, @RequestParam MultipartFile file) {
        userStoryService.uploadUserStoryAttachment(userStoryId, file);
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/sorted")
    public ResponseEntity<List<UserStoryGetDto>> getSortedAndPagedUserStories() {
        return ResponseEntity.ok().body(userStoryService
                .getPaginatedAndSortedUserStories
                        (PageRequest.of(0, 10,
                                Sort.by(Sort.Order.asc("name")))));
    }

    @PostMapping
    public ResponseEntity<Void> addUserStory(@RequestBody UserStoryPostDto userStoryPostDto,
                                             @RequestParam(required = false) Long sprintId) {
        if (sprintId != null)
            userStoryService.addUserStory(userStoryPostDto, sprintId);
        else
            publisher.publishEvent(new UserStoryCreatedEvent(userStoryService.addUserStory(userStoryPostDto)));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStoryById(id);
        return ResponseEntity.ok().build();
    }
}
