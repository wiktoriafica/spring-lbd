package pl.fis.springlbdday2.controller.sprint;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fis.springlbdday2.dto.sprint.SprintGetDto;
import pl.fis.springlbdday2.dto.sprint.SprintPostDto;
import pl.fis.springlbdday2.entity.enums.SprintStatus;
import pl.fis.springlbdday2.service.sprint.SprintService;

import java.util.List;


@RestController
@RequestMapping(path = "/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;
    @GetMapping
    public ResponseEntity<List<SprintGetDto>> getSprints(@RequestParam(required = false,
            defaultValue = "false") boolean tasks) {
        return ResponseEntity.ok().body(sprintService.getSprints(tasks));
    }

    @GetMapping(path = "{id}/storyPoints")
    public ResponseEntity<Integer> getSprintsStoryPoints(@PathVariable Long id) {
        return ResponseEntity.ok().body(sprintService.getStoryPointsFromSprint(id));
    }

    @GetMapping(path = "/fromGivenTime")
    public ResponseEntity<List<SprintGetDto>> getSprintsFromGivenTime(
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.ok().body(sprintService
                .getSprintsFromGivenTime(start, end));
    }

    @PutMapping(path = "{id}/{status}")
    public ResponseEntity<Void> updateSprintStatus(@PathVariable Long id,
                                                   @PathVariable SprintStatus status) {
        SprintPostDto sprintPostDto = new SprintPostDto();
        sprintPostDto.setStatus(status);
        sprintService.updateSprint(id, sprintPostDto);
        return ResponseEntity.ok().build();
    }
}
