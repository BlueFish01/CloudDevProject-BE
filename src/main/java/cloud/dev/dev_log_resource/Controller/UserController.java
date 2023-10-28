package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.service.JwtService;
import cloud.dev.dev_log_resource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @PostMapping("/up-profile")
    public ResponseEntity<ResponseModel> updateProfile(@RequestParam("file") MultipartFile image, @CurrentSecurityContext(expression = "authentication") Authentication authentication) throws IOException {

        userService.updateProfile(image, jwtService.getUsername(authentication));
        return ResponseHelper.success();

    }


}
