package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.dto.PostDto;
import cloud.dev.dev_log_resource.entity.PostDynamoEntity;
import cloud.dev.dev_log_resource.entity.PostEntity;
import cloud.dev.dev_log_resource.repository.PostDao;
import cloud.dev.dev_log_resource.repository.PostDynamoRepository;
import cloud.dev.dev_log_resource.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostDynamoRepository postDynamoRepository;
    @Autowired
    PostDao postDao;
    @Autowired
    AwsS3Service awsS3Service;

    @Value("${spring.amazon.aws.s3Url}")
    private String s3Url;
    public void create(PostDto postDto, MultipartFile image, Authentication authentication) throws IOException {
        int postId;
        Integer userId = userService.getUserId(jwtService.getUsername(authentication));

        List<PostDto> lastId = postDao.getLastID();
        try{
            lastId.get(0).getPostId();
            postId = lastId.get(0).getPostId() + 1;
        }
        catch (Exception e){
            postId = 1;
        }


        PostEntity postEntity = new PostEntity();
        postEntity.setPostId(postId);
        postEntity.setPostOwner(userId);
        postEntity.setPostTitle(postDto.getPostTitle());
        postRepository.save(postEntity);

        String imageFileName = "post-" + postId;
        String postCoverUrl = s3Url + imageFileName;

        PostDynamoEntity postDynamoEntity = new PostDynamoEntity();
        postDynamoEntity.setUserId(userId);
        postDynamoEntity.setPostId(postId);
        postDynamoEntity.setPostTitle(postDto.getPostTitle());
        postDynamoEntity.setPostDescription(postDto.getPostDescription());
        postDynamoEntity.setPostContent(postDto.getPostContent());
        postDynamoEntity.setPostCover(postCoverUrl);
        postDynamoEntity.setPostCreateDate(new Timestamp(System.currentTimeMillis()));
        postDynamoRepository.savePost(postDynamoEntity);



        awsS3Service.putImage(image, imageFileName);

    }




}
