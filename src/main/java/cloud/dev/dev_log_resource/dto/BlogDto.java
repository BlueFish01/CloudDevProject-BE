package cloud.dev.dev_log_resource.dto;

import lombok.Data;

@Data
public class BlogDto {
    private Integer blogId;
    private Integer ownerId;
    private String blogTitle;
    private String blogContent;
    private String blogDescription;
    private String blogCover;
}
