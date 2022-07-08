package pl.fis.springlbdday2.controller.userstory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.fis.springlbdday2.dto.userstory.UserStoryGetDto;
import pl.fis.springlbdday2.dto.userstory.UserStoryPostDto;
import pl.fis.springlbdday2.service.userstory.UserStoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/userstories")
public class UserStoryController {
    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

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

    @GetMapping(path = "/sorted")
    public ResponseEntity<List<UserStoryGetDto>> getSortedAndPagedUserStories() {
        return ResponseEntity.ok().body(userStoryService
                .getPaginatedAndSortedUserStories
                        (PageRequest.of(0, 10,
                                Sort.by(Sort.Order.asc("name")))));
    }

    @PostMapping
    public ResponseEntity<Void> addUserStory(@RequestBody UserStoryPostDto userStoryPostDto,
                                             @RequestParam Long sprintId) {
        userStoryService.addUserStory(userStoryPostDto, sprintId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStoryById(id);
        return ResponseEntity.ok().build();
    }
}
