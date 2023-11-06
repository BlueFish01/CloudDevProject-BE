package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.dto.UserDto;
import cloud.dev.dev_log_resource.entity.UserEntity;
import cloud.dev.dev_log_resource.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AwsS3Service awsS3Service;

    @Autowired
    JwtService jwtService;

    @Value("${spring.amazon.aws.s3Url}")
    private String s3Url;


    public Integer getUserId(String cUid) throws Exception{

        try{
            log.info("Start UserService.getUserId()");
            UserEntity userEntity = userRepository.findByCUid(cUid);
            return userEntity.getUserId();
        }
        catch(Exception e){
            log.info("Error UserService.getUserId()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.getUserId()");
        }
    }


//    public void updateProfile(MultipartFile image, String cUid) throws Exception {
//
//        try{
//            log.info("Start UserService.updateProfile()");
//
//            String imageFileName = "user-profile-" + getUserId(cUid);
//            String profileUrl = s3Url + imageFileName;
//
//            awsS3Service.putImage(image, imageFileName);
//
//
//            UserEntity user = userRepository.findByCUid(cUid);
//            user.setUserPicture(profileUrl);
//            userRepository.save(user);
//
//
//        }
//        catch(Exception e){
//            log.info("Error UserService.updateProfile()");
//            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
//        }
//        finally {
//            log.info("End UserService.updateProfile()");
//        }
//
//
//    }

    public UserEntity getUserProfile(Authentication authentication) throws Exception{

        String cUid = jwtService.getUsername(authentication);
        try {
            log.info("Start UserService.getUserProfile()");

            UserEntity userEntity = userRepository.findByCUid(cUid);

            return  userEntity;
        }
        catch(Exception e){
            log.info("Error UserService.getUserProfile()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.getUserProfile()");
        }


    }


    public UserEntity getUserProfileById(String userId) throws Exception{

        try {
            log.info("Start UserService.getUserProfile()");

            UserEntity userEntity = userRepository.findById(Integer.valueOf(userId)).get();

            return  userEntity;
        }
        catch(Exception e){
            log.info("Error UserService.getUserProfile()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.getUserProfile()");
        }


    }

    public UserEntity editProfile(UserDto userDto, MultipartFile image, Authentication authentication) throws Exception{

        try {
            log.info("Start UserService.editProfile()");
            String cUid = jwtService.getUsername(authentication);

            UserEntity userEntity = userRepository.findByCUid(cUid);
            if(userDto.getUserFName() != null){
                userEntity.setUserFname(userDto.getUserFName());
            }

            if(userDto.getUserLName() != null){
                userEntity.setUserLname(userDto.getUserLName());
            }

            if(userDto.getUserSocial() != null){
                userEntity.setUserSocial(userDto.getUserSocial());
            }

            if(userDto.getUserAbout() != null){
                userEntity.setUserAbout(userDto.getUserAbout());
            }

            if(userDto.getDeleteImage().equals("True")){
                userEntity.setUserPicture(null);
            }

            else {
                String imageFileName = "user-profile-" + getUserId(cUid);
                String profileUrl = s3Url + imageFileName;
                userEntity.setUserPicture(profileUrl);
                awsS3Service.putImage(image, imageFileName);
            }

            return userRepository.save(userEntity);


        }
        catch(Exception e){
            log.info("Error UserService.editProfile()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.editProfile()");
        }
    }
}
