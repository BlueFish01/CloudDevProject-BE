package cloud.dev.dev_log_resource.entity;

import cloud.dev.dev_log_resource.Util.BlogContentConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.Data;

import java.util.Map;

@Data
@DynamoDBTable(tableName = "devlog_blog")
public class BlogDynamoEntity {

    @DynamoDBHashKey(attributeName = "user_id")
    private Integer userId;

    @DynamoDBAttribute(attributeName = "owner_username")
    private String owner_username;

    @DynamoDBRangeKey(attributeName = "blog_id")
    private Integer blogId;

    @DynamoDBAttribute(attributeName = "blog_title")
    private String blogTitle;


    @DynamoDBAttribute(attributeName = "blog_description")
    private String blogDescription;

//    @DynamoDBTypeConverted(converter = BlogContentConverter.class)
    @DynamoDBAttribute(attributeName = "blog_content")
    private String blogContent;

    @DynamoDBAttribute(attributeName = "blog_cover")
    private String blogCover;

    @DynamoDBAttribute(attributeName = "blog_create_date")
    private String blogCreateDate;

    @DynamoDBAttribute(attributeName = "blog_edit_date")
    private String blogEditDate;



}
