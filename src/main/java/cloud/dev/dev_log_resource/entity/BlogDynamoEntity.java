package cloud.dev.dev_log_resource.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "devlog_blog")
public class BlogDynamoEntity {

    @DynamoDBHashKey(attributeName = "user_id")
    private Integer userId;

    @DynamoDBRangeKey(attributeName = "blog_id")
    private Integer blogId;

    @DynamoDBAttribute(attributeName = "blog_title")
    private String blogTitle;

    @DynamoDBAttribute(attributeName = "blog_description")
    private String blogDescription;

    @DynamoDBAttribute(attributeName = "blog_content")
    private String blogContent;

    @DynamoDBAttribute(attributeName = "blog_cover")
    private String blogCover;

    @DynamoDBAttribute(attributeName = "blog_create_date")
    private String blogCreateDate;

    @DynamoDBAttribute(attributeName = "blog_edit_date")
    private String blogEditDate;



}
