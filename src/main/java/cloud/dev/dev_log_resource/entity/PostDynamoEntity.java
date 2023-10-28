package cloud.dev.dev_log_resource.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

@Data
@DynamoDBTable(tableName = "post")
public class PostDynamoEntity {

    @DynamoDBHashKey(attributeName = "user_id")
    private Integer userId;

    @DynamoDBRangeKey(attributeName = "post_id")
    private Integer postId;

    @DynamoDBAttribute(attributeName = "post_title")
    private String postTitle;

    @DynamoDBAttribute(attributeName = "post_description")
    private String postDescription;

    @DynamoDBAttribute(attributeName = "post_content")
    private String postContent;

    @DynamoDBAttribute(attributeName = "post_cover")
    private String postCover;

    @DynamoDBAttribute(attributeName = "post_create_date")
    private Timestamp postCreateDate;

    @DynamoDBAttribute(attributeName = "post_edit_date")
    private Timestamp postEditDate;



}
