package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.entity.BlogDynamoEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostDynamoRepository{

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void savePost(BlogDynamoEntity blogDynamoEntity) {
        dynamoDBMapper.save(blogDynamoEntity);
    }

    public BlogDynamoEntity getPostById(Integer userId, Integer postId) {
        return dynamoDBMapper.load(BlogDynamoEntity.class, userId, postId);
    }

}


//@EnableScan
//public interface PostDynamoRepository extends CrudRepository<PostDynamoEntity, Integer> {
//
////    @Autowired
////    private DynamoDBMapper dynamoDBMapper;
////
////    public void savePost(PostDynamoEntity postDynamoEntity) {
////        dynamoDBMapper.save(postDynamoEntity);
////    }
//}