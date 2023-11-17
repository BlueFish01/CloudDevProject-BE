package cloud.dev.dev_log_resource.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JwtService {


    public String getUsername(Authentication authentication) throws Exception{
        try{
            log.info("Start JwtService.getUsername()");

            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {

                // Get the username from the JWT claims
                // You can also access other claims as needed
                // For example, if the username claim is stored under a different key, replace "sub" with the correct claim key.

                return jwt.getClaim("sub");
            }
            else {
                return null;
            }

        }

        catch(Exception e){
            log.info("Error JwtService.getUsername()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End JwtService.getUsername()");
        }

    }
}
