package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.dto.PostDto;
import cloud.dev.dev_log_resource.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<ResponseModel> createPost(@RequestParam("json") String json, @RequestParam("file") MultipartFile image, @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PostDto postDto = objectMapper.readValue(json, PostDto.class);
        postService.create(postDto, image, authentication);
        return ResponseHelper.success();

    }





    @GetMapping("/get-post")
    public ResponseEntity<ResponseModel> getPost(@RequestParam int postId, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
        return null;

    }






    @PostMapping("/edit-post")
    public ResponseEntity<ResponseModel> editPost(@RequestParam int postId, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
        return null;

    }

}
