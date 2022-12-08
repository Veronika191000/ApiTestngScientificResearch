package models.search;

import lombok.Data;

import java.util.List;

@Data
public class Options {
    public List<Term> terms;
    public List<Object> modifiers;
    public List<String> modelTypes;
    public boolean partial;
}
