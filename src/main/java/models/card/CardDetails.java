package models.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import models.common.Limits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class CardDetails {
    private String id;
    private Badges badges;
    private ArrayList<Object> checkItemStates;
    private boolean closed;
    private boolean dueComplete;
    private Date dateLastActivity;
    private String desc;
    private DescData descData;
    private Object due;
    private Object dueReminder;
    private Object email;
    private String idBoard;
    private List<Object> idChecklists;
    private String idList;
    private List<Object> idMembers;
    private List<Object> idMembersVoted;
    private int idShort;
    private Object idAttachmentCover;
    private List<Object> labels;
    private List<Object> idLabels;
    private boolean manualCoverAttachment;
    private String name;
    private int pos;
    private String shortLink;
    private String shortUrl;
    private Object start;
    private boolean subscribed;
    private String url;
    private Cover cover;
    @JsonProperty
    private boolean isTemplate;
    private Object cardRole;
    private List<Object> attachments;
    private List<Object> stickers;
    private Limits limits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardDetails)) return false;
        CardDetails that = (CardDetails) o;
        return getPos() == that.getPos() &&
                Objects.equals(getId(), that.getId()) /*&&
                Objects.equals(getDateLastActivity(), that.getDateLastActivity()) &&
                Objects.equals(getDesc(), that.getDesc()) &&
                Objects.equals(getDescData(), that.getDescData()) &&
                Objects.equals(getDue(), that.getDue()) &&
                Objects.equals(getIdBoard(), that.getIdBoard()) &&
                Objects.equals(getIdList(), that.getIdList()) &&
                Objects.equals(getName(), that.getName()*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId()/*, getDateLastActivity(), getDesc(), getDescData(), getDue(), getIdBoard(), getIdList(), getName(), getPos()*/);
    }
}
