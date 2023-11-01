package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.dto.BlogDto;
import cloud.dev.dev_log_resource.entity.BlogDynamoEntity;
import cloud.dev.dev_log_resource.entity.BlogEntity;
import cloud.dev.dev_log_resource.repository.BlogDao;
import cloud.dev.dev_log_resource.repository.PostDynamoRepository;
import cloud.dev.dev_log_resource.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class BlogService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    PostDynamoRepository postDynamoRepository;
    @Autowired
    BlogDao blogDao;
    @Autowired
    AwsS3Service awsS3Service;

    @Value("${spring.amazon.aws.s3Url}")
    private String s3Url;

    public BlogDynamoEntity createBlog(BlogDto blogDto, MultipartFile image, Authentication authentication) throws Exception {
        try {
            log.info("Start PostService.create()");

            int blogId;
            Integer userId = userService.getUserId(jwtService.getUsername(authentication));

            List<BlogDto> lastId = blogDao.getLastID();
            try{
                lastId.get(0).getBlogId();
                blogId = lastId.get(0).getBlogId() + 1;
            }
            catch (Exception e){
                blogId = 1;
            }


            BlogEntity blogEntity = new BlogEntity();
            blogEntity.setPostId(blogId);
            blogEntity.setBlogOwner(userId);
            blogEntity.setBlogTitle(blogDto.getBlogTitle());
            blogRepository.save(blogEntity);

            String imageFileName = "post-" + blogId;
            String postCoverUrl = s3Url + imageFileName;

            BlogDynamoEntity blogDynamoEntity = new BlogDynamoEntity();
            blogDynamoEntity.setUserId(userId);
            blogDynamoEntity.setBlogId(blogId);
            blogDynamoEntity.setBlogTitle(blogDto.getBlogTitle());
            blogDynamoEntity.setBlogDescription(blogDto.getBlogDescription());
            blogDynamoEntity.setBlogContent(blogDto.getBlogContent());
            blogDynamoEntity.setBlogCover(postCoverUrl);
            blogDynamoEntity.setBlogCreateDate(String.valueOf(new Timestamp(System.currentTimeMillis())));
            postDynamoRepository.savePost(blogDynamoEntity);
            awsS3Service.putImage(image, imageFileName);

            return postDynamoRepository.getPostById(userId, blogId);

        }
        catch (Exception e) {
            log.info("Error PostService.create()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }

        finally {
            log.info("End PostService.create()");
        }
    }

    //getPost




    //edit Post












}
