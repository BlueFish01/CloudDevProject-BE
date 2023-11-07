package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.dto.BlogDto;
import cloud.dev.dev_log_resource.entity.BlogDynamoEntity;
import cloud.dev.dev_log_resource.service.BlogService;
import com.amazonaws.services.xray.model.Http;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    //Home page //get-blog
    @GetMapping("/blog-list")
    public ResponseEntity<ResponseModel> getBlogList(@RequestParam String sort, //latest, popular, oldest
                                                     @RequestParam int limit,
                                                     @CurrentSecurityContext(expression = "authentication") Authentication authentication
                                                 )throws Exception {
        try {
            log.info("Error BlogController.getHomePage()");
            List<BlogDto> listBlog = blogService.getBlogList(sort, limit, authentication);
            return ResponseHelper.success(listBlog);
        }
        catch (Exception e){
            log.info("Error BlogController.getHomePage()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        }

        finally {
            log.info("End BlogController.getHomePage()");
        }
    }


    @GetMapping("/get-blog")
    public ResponseEntity<ResponseModel> getBlogById(@RequestParam int blogId) {
        try {
            log.info("Error BlogController.getBlogById()");
            BlogDto blog = blogService.getBlogById(blogId);
            return ResponseHelper.success(blog);
        }
        catch (Exception e){
            log.info("Error BlogController.getBlogById()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        }

        finally {
            log.info("End BlogController.getBlogById()");
        }
    }


    //Blog Editor
    @PostMapping("/create-blog")
    public ResponseEntity<ResponseModel> createPost(@RequestParam("json") String json,
                                                    @RequestParam("file") MultipartFile image,
                                                    @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws Exception{

        try {
            log.info("Start BlogController.createPost()");

            ObjectMapper objectMapper = new ObjectMapper();
            BlogDto blogDto = objectMapper.readValue(json, BlogDto.class);
            BlogDto result = blogService.createBlog(blogDto, image, authentication);
            return ResponseHelper.success(result);

        }
        catch (Exception e){
            log.info("Error BlogController.createPost()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End BlogController.createPost()");
        }
    }




    @PutMapping("/edit-blog")
    public ResponseEntity<ResponseModel> editBlog(@RequestBody BlogDto blogDto,
                                                  @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws Exception{
        try {
            log.info("Start BlogController.editBlog()");
            BlogDto result =  blogService.editBLog(blogDto, authentication);
            return ResponseHelper.success(result);

        }
        catch (Exception e){
            log.info("Error BlogController.editBlog()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End BlogController.editBlog()");
        }

    }

    @DeleteMapping("/delete-blog")
    public ResponseEntity<ResponseModel> deleteBlog(@RequestBody BlogDto blogDto,
                                                    @CurrentSecurityContext(expression = "authentication") Authentication authentication
                                                    ) throws Exception{

        try {
            log.info("Start BlogController.deleteBlog()");
            blogService.deleteBlog(blogDto.getBlogId(), authentication);
            return ResponseHelper.success();

        }
        catch (Exception e){
            log.info("Error BlogController.deleteBlog()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End BlogController.deleteBlog()");
        }

    }


}
