package cloud.dev.dev_log_resource.dto;

import lombok.Data;

@Data
public class PostDto {
    private Integer postId;
    private Integer ownerId;
    private String postTitle;
    private String postContent;
    private String postDescription;
    private String postCover;
}
