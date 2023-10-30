package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.dto.PostDto;
import cloud.dev.dev_log_resource.entity.PostDynamoEntity;
import cloud.dev.dev_log_resource.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

import java.io.File;
import java.io.IOException;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<ResponseModel> createPost(@RequestParam("json") String json, @RequestParam("file") MultipartFile image, @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws Exception, HttpException {

        try {
            log.info("Start PostController.createPost()");

            ObjectMapper objectMapper = new ObjectMapper();
            PostDto postDto = objectMapper.readValue(json, PostDto.class);
            PostDynamoEntity result = postService.create(postDto, image, authentication);
            return ResponseHelper.success(result);

        }
        catch (Exception e){
            log.info("Error PostController.createPost()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End PostController.createPost()");
        }
    }





    @GetMapping("/get-post")
    public ResponseEntity<ResponseModel> getPost(@RequestParam int postId, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
        try {
            log.info("Start PostController.Get Post()");
            PostDynamoEntity result = postService.getPost(postId, authentication);
            return ResponseHelper.success(result);
        }
        catch(Exception e) {
            log.info("Error PostController.Get-Post");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

    }



    @PutMapping("/edit-post")
    public ResponseEntity<ResponseModel> editPost(@RequestParam("json") String json, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
        try{
            log.info("Start PostController.Get Post()");
            ObjectMapper objectMapper = new ObjectMapper();
            PostDto postDto = objectMapper.readValue(json, PostDto.class);
            PostDynamoEntity result = postService.editPost(postDto,authentication);
            return ResponseHelper.success(result);
        }
        catch (Exception e) {
            log.info("Error PostController.Get-Post");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

    }

}
