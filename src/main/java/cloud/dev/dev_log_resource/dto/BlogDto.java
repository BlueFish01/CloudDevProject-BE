package cloud.dev.dev_log_resource.dto;

import lombok.Data;

@Data
public class BlogDto {
    private Integer blogId;
    private Integer ownerId;
    private String ownerUserName;
    private String blogTitle;
    private String blogCover;
    private String blogDescription;
    private Object blogContent;
    private Integer blogView;
    private String blogCreateDate;
    private String blogEditDate;
}


