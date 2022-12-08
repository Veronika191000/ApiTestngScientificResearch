package models.checklist;

import lombok.Data;

import java.util.List;

@Data
public class Checklist {
    private String id;
    private String name;
    private String idBoard;
    private String idCard;
    private int pos;
    private List<Object> checkItems;
}
