package cloud.dev.dev_log_resource.Controller;

import cloud.dev.dev_log_resource.Util.ResponseHelper;
import cloud.dev.dev_log_resource.Util.ResponseModel;
import cloud.dev.dev_log_resource.dto.UserDto;
import cloud.dev.dev_log_resource.repository.PostDynamoRepository;
import cloud.dev.dev_log_resource.service.JwtService;
import cloud.dev.dev_log_resource.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TestController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PostDynamoRepository postDynamoRepository;



    @GetMapping("/hello")
    public ResponseEntity<ResponseModel> hello(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseHelper.success(userService.getUserDetail(jwtService.getUsername(authentication)));
    }

}
