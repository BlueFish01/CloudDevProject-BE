package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.dto.UserDto;
import cloud.dev.dev_log_resource.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;



    //Get Profile
    @GetMapping("/get-profile")
    public ResponseEntity<ResponseModel> getProfile(@CurrentSecurityContext(expression = "authentication") Authentication authentication
                                                    ) throws Exception {
        try {
            log.info("Start UserController.getProfile()");

            UserDto userDto = userService.getUserProfile(authentication);


            return ResponseHelper.success(userDto);

        }

        catch (Exception e){
            log.info("Error UserController.getProfile()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End UserController.getProfile()");
        }
    }



    @GetMapping("/get-profile-by-id")
    public ResponseEntity<ResponseModel> getProfileById(@RequestParam(required = false) String userId) throws Exception {
        try {
            log.info("Start UserController.getProfileById()");


            UserDto userDto = userService.getUserProfileById(userId);


            return ResponseHelper.success(userDto);

        }

        catch (Exception e){
            log.info("Error UserController.getProfileById()");
            return ResponseHelper.badRequest(HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        }

        finally {
            log.info("End UserController.getProfileById()");
        }
    }







    //Edit Profile








    //Edit Profile
    @PutMapping("/edit-profile")
    public ResponseEntity<ResponseModel> editProfile(@RequestParam(value = "file", required = false) MultipartFile image,
                                                       @RequestParam(value = "json", required = false) String json,
                                                       @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws Exception {
        try {
            log.info("Start UserController.updateProfile()");

            if(json != null){
                ObjectMapper objectMapper = new ObjectMapper();
                UserDto userDto = objectMapper.readValue(json, UserDto.class);
                userService.editProfileContent(userDto, authentication);
            }

            if(image != null)
            {
               userService.updateProfileImage(image, authentication);
            }

            UserDto userDto = userService.getUserProfile(authentication);




            return ResponseHelper.success(userDto);
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
