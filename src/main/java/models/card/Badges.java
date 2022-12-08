package models.card;

import lombok.Data;

@Data
public class Badges {
    private AttachmentsByType attachmentsByType;
    private boolean location;
    private int votes;
    private boolean viewingMemberVoted;
    private boolean subscribed;
    private String fogbugz;
    private int checkItems;
    private int checkItemsChecked;
    private Object checkItemsEarliestDue;
    private int comments;
    private int attachments;
    private boolean description;
    private Object due;
    private boolean dueComplete;
    private Object start;
}
