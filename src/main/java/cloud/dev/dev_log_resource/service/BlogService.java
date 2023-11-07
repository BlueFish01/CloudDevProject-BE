package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.dto.BlogDto;
import cloud.dev.dev_log_resource.entity.BlogDynamoEntity;
import cloud.dev.dev_log_resource.entity.BlogEntity;
import cloud.dev.dev_log_resource.repository.BlogDao;
import cloud.dev.dev_log_resource.repository.BlogDynamoRepository;
import cloud.dev.dev_log_resource.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.util.ArrayList;
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
    BlogDynamoRepository blogDynamoRepository;
    @Autowired
    BlogDao blogDao;
    @Autowired
    AwsS3Service awsS3Service;

    @Value("${spring.amazon.aws.s3Url}")
    private String s3Url;

    public BlogDto createBlog(BlogDto blogDto, MultipartFile image, Authentication authentication) throws Exception {
        try {
            log.info("Start BlogService.create()");

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
            blogEntity.setBlogId(blogId);
            blogEntity.setBlogOwner(userId);
            blogEntity.setBlogTitle(blogDto.getBlogTitle());
            blogEntity.setBlogView(0);
            blogRepository.save(blogEntity);

            String imageFileName = "PostCover-" + blogId;
            String postCoverUrl = s3Url + imageFileName;

            BlogDynamoEntity blogDynamoEntity = new BlogDynamoEntity();
            blogDynamoEntity.setUserId(userId);
            blogDynamoEntity.setBlogId(blogId);
            blogDynamoEntity.setBlogTitle(blogDto.getBlogTitle());
            blogDynamoEntity.setBlogDescription(blogDto.getBlogDescription());
            blogDynamoEntity.setBlogContent(blogDto.getBlogContent());
            blogDynamoEntity.setBlogCover(postCoverUrl);
            blogDynamoEntity.setBlogCreateDate(String.valueOf(new Timestamp(System.currentTimeMillis())));
            blogDynamoRepository.savePost(blogDynamoEntity);
            awsS3Service.putImage(image, imageFileName);

            BlogDynamoEntity dynamo = blogDynamoRepository.getPostById(userId, blogId);
            BlogEntity blog = blogRepository.getReferenceById(blogId);
            BlogDto result = new BlogDto();
            result.setBlogId(blogId);
            result.setBlogTitle(blog.getBlogTitle());
            result.setBlogDescription(dynamo.getBlogDescription());
            result.setBlogCover(dynamo.getBlogCover());
            result.setBlogContent(dynamo.getBlogContent());
            result.setBlogView(blog.getBlogView());
            result.setBlogCreateDate(blog.getBlogCreateDate().toString());
            if(blog.getBlogEditDate() != null){
                result.setBlogEditDate(blog.getBlogEditDate().toString());
            }




            return result;

        }
        catch (Exception e) {
            log.info("Error BlogService.create()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }

        finally {
            log.info("End BlogService.create()");
        }
    }

    public BlogDto editBLog(BlogDto blogDto, Authentication authentication) throws Exception {

        try{
            log.info("Start BlogService.editBLog()");
            Integer userId = userService.getUserId(jwtService.getUsername(authentication));
            BlogEntity blogEntity = blogRepository.getReferenceById(blogDto.getBlogId());
            BlogDynamoEntity blogDynamoEntity = blogDynamoRepository.getPostById(userId, blogDto.getBlogId());

            if(blogDto.getBlogTitle() != null){
                blogEntity.setBlogTitle(blogDto.getBlogTitle());
                blogDynamoEntity.setBlogTitle(blogDto.getBlogTitle());
            }

            if(blogDto.getBlogDescription() != null){
                blogDynamoEntity.setBlogDescription(blogDto.getBlogDescription());
            }

            if(blogDto.getBlogContent() != null){
                blogDynamoEntity.setBlogContent(blogDto.getBlogContent());
            }

            blogDynamoEntity.setBlogEditDate(String.valueOf(new Timestamp(System.currentTimeMillis())));

            blogDynamoRepository.savePost(blogDynamoEntity);
            BlogEntity blog = blogRepository.save(blogEntity);

            BlogDynamoEntity dynamo = blogDynamoRepository.getPostById(userId, blogDto.getBlogId());
            BlogDto result = new BlogDto();
            result.setBlogId(blog.getBlogId());
            result.setBlogTitle(blog.getBlogTitle());
            result.setBlogCover(dynamo.getBlogCover());
            result.setBlogDescription(dynamo.getBlogDescription());
            result.setBlogContent(dynamo.getBlogContent());
            result.setBlogView(blog.getBlogView());
            result.setBlogCreateDate(String.valueOf(blog.getBlogCreateDate()));
            result.setBlogEditDate(String.valueOf(blog.getBlogEditDate()));

            return result;
        }
        catch (Exception e) {
            log.info("Error BlogService.editBLog()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End BlogService.editBLog()");
        }


    }


    public void deleteBlog(Integer blogId, Authentication authentication) throws Exception{

        try{
            log.info("Start BlogService.deleteBlog()");
            Integer userId = userService.getUserId(jwtService.getUsername(authentication));
            blogRepository.deleteById(blogId);
            blogDynamoRepository.deleteBlogById(blogId, userId);
            awsS3Service.deleteObject("PostCCover-" + blogId);

        }
        catch (Exception e) {
            log.info("Error BlogService.deleteBlog()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End BlogService.deleteBlog()");
        }


    }

    public List<BlogDto> getBlogList(String sort, int limit, Authentication authentication) throws Exception {
        try {
            log.info("Start BlogService.getBlogList()");
            List<BlogDto> bloglist = new ArrayList<>();
            Integer userId = userService.getUserId(jwtService.getUsername(authentication));
            //sort = latest
            if (sort.equals("latest")) {
                bloglist = blogDao.getBlogByLatest(limit, userId);
            } else if (sort.equals("popular")) {
                bloglist = blogDao.getBlogByPopular(limit, userId);
            } else if (sort.equals("oldest")) {
                bloglist = blogDao.getBlogByOldest(limit, userId);
            }

            for(BlogDto blog : bloglist) {
                BlogDynamoEntity blogDynamoEntity = blogDynamoRepository.getPostById(blog.getOwnerId(),blog.getBlogId());
                blog.setBlogCover(blogDynamoEntity.getBlogCover());
                blog.setBlogDescription(blogDynamoEntity.getBlogDescription());
            }

            return bloglist;

        } catch (Exception e) {
            log.info("Error BlogService.getBlogList()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } finally {
            log.info("End BlogService.getBlogList()");
        }
    }

    public BlogDto getBlogById(int blogId) throws Exception{

        try {
            log.info("Start BlogService.getBlogById()");

            BlogEntity blogEntity = blogRepository.getBlogOwnerId(blogId);
            blogEntity.setBlogView(blogEntity.getBlogView() + 1);
            blogEntity =  blogRepository.save(blogEntity);

            int userId = blogEntity.getBlogOwner();
            BlogDynamoEntity blogDynamoEntity = blogDynamoRepository.getPostById(userId, blogId);

            BlogDto blogDto = new BlogDto();
            blogDto.setBlogId(blogEntity.getBlogId());
            blogDto.setBlogTitle(blogDynamoEntity.getBlogTitle());
            blogDto.setBlogDescription(blogDynamoEntity.getBlogDescription());
            blogDto.setBlogContent(blogDynamoEntity.getBlogContent());
            blogDto.setBlogCover(blogDynamoEntity.getBlogCover());
            blogDto.setBlogView(blogEntity.getBlogView());
            blogDto.setBlogCreateDate(blogDynamoEntity.getBlogCreateDate());
            blogDto.setBlogEditDate(blogDynamoEntity.getBlogEditDate());

            return blogDto;

        } catch (Exception e) {
            log.info("Error BlogService.getBlogById()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } finally {
            log.info("End BlogService.getBlogById()");
        }
    }
}
