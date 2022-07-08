package pl.fis.springlbdday2.dto.sprint;

import com.sun.istack.NotNull;
import pl.fis.springlbdday2.entity.enums.SprintStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class SprintPostDto implements Serializable {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goalDescription;
    private SprintStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public SprintStatus getStatus() {
        return status;
    }

    public void setStatus(SprintStatus status) {
        this.status = status;
    }
}
