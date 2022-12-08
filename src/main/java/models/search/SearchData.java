package models.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchData {
    private Options options;
    private List<Board> boards;
    private List<Object> cards;
    private List<Object> organizations;
    private List<Object> members;
}
