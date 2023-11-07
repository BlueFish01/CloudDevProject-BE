package cloud.dev.dev_log_resource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resource.jwk.issuer-uri}")
    private String jwkSetUri;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/user/get-profile").authenticated()
                .antMatchers("/api/user/get-profile-by-id").permitAll()
                .antMatchers("/api/blog/create-blog").authenticated()
                .antMatchers("/api/blog/edit-blog").authenticated()
                .antMatchers("/api/blog/delete-blog").authenticated()
                .antMatchers("/api/blog/get-blog").permitAll()
                .antMatchers("/api/blog/list-blog").permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(jwkSetUri);
    }


}
