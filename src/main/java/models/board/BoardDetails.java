package models.board;

import lombok.*;
import models.common.Limits;

@Data
public class BoardDetails {
    private String id;
    private String name;
    private String desc;
    private Object descData;
    private boolean closed;
    private String idOrganization;
    private Object idEnterprise;
    private boolean pinned;
    private String url;
    private String shortUrl;
    private Prefs prefs;
    private LabelNames labelNames;
    private Limits limits;
}