package models.list;

import lombok.Data;

@Data
public class ListDetails {
    private String id;
    private String name;
    private boolean closed;
    private String idBoard;
    private int pos;
 //   private Limits limits;
}
