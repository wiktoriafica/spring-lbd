package pl.fis.springlbdday2.event;

public class UserStoryCreatedEvent {
    private Long userCreatedStoryId;

    public UserStoryCreatedEvent(Long userCreatedStoryId) {
        this.userCreatedStoryId = userCreatedStoryId;
    }

    public Long getUserCreatedStoryId() {
        return userCreatedStoryId;
    }

    public void setUserCreatedStoryId(Long userCreatedStoryId) {
        this.userCreatedStoryId = userCreatedStoryId;
    }
}
