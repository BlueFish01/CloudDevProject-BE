package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.dto.BlogDto;
import cloud.dev.dev_log_resource.dto.UserDto;
import cloud.dev.dev_log_resource.entity.UserEntity;
import cloud.dev.dev_log_resource.service.JwtService;
import cloud.dev.dev_log_resource.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;



    //Get Profile
    @GetMapping("/get-profile")
    public ResponseEntity<ResponseModel> getProfile(@RequestParam(required = false) String userId,
                                                    @CurrentSecurityContext(expression = "authentication") Authentication authentication
                                                    ) throws Exception {
        try {
            log.info("Start UserController.getProfile()");

            UserEntity userEntity = new UserEntity();

            if(userId == null){
                userEntity = userService.getUserProfile(authentication);

            }
            else {
                userEntity = userService.getUserProfileById(userId);

            }

            return ResponseHelper.success(userEntity);

        }

        catch (Exception e){
            log.info("Error UserController.getProfile()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End UserController.getProfile()");
        }
    }







    //Edit Profile
    @PutMapping("/edit-profile")
    public ResponseEntity<ResponseModel> editProfile(@RequestParam(value = "file", required = false) MultipartFile image,
                                                       @RequestParam(value = "json", required = false) String json,
                                                       @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws Exception {
        try {
            log.info("Start UserController.updateProfile()");

            ObjectMapper objectMapper = new ObjectMapper();
            UserDto userDto = objectMapper.readValue(json, UserDto.class);
            UserEntity userEntity = userService.editProfile(userDto, image, authentication);

            return ResponseHelper.success(userEntity);
        }

        catch (Exception e){
            log.info("Error UserController.updateProfile()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End UserController.updateProfile()");
        }

    }


}
