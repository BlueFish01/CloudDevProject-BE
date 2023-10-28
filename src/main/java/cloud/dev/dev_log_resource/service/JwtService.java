package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.entity.PostDynamoEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {


    public String getUsername(Authentication authentication){
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {

            // Get the username from the JWT claims
            String username = jwt.getClaim("sub");
            PostDynamoEntity test = new PostDynamoEntity();
            // You can also access other claims as needed
            // For example, if the username claim is stored under a different key, replace "sub" with the correct claim key.

            return username;
        }
        else {
            return null;
        }
    }
}
