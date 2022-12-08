package models.board;

import lombok.Data;

@Data
public class SwitcherView {
    private String _id;
    private String viewType;
    private boolean enabled;
}
