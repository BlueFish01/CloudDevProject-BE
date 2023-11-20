package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.dto.UserDto;
import cloud.dev.dev_log_resource.entity.UserEntity;
import cloud.dev.dev_log_resource.repository.UserDao;
import cloud.dev.dev_log_resource.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AwsS3Service awsS3Service;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDao userDao;

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

    public UserDto getUserProfile(Authentication authentication) throws Exception{


        try {
            log.info("Start UserService.getUserProfile()");

            String cUid = jwtService.getUsername(authentication);
            UserEntity userEntity = userRepository.findByCUid(cUid);
            int blogCount = userDao.getBlogCount(getUserId(cUid)).get(0).getNumberOfPost();

            UserDto result = new UserDto();
            result.setUser_id(userEntity.getUserId());
            result.setUsername(userEntity.getUsername());
            result.setUserFName(userEntity.getUserFname());
            result.setUserLName(userEntity.getUserLname());
            result.setUserEmail(userEntity.getUserEmail());
            result.setUserAbout(userEntity.getUserAbout());
            result.setUserSocial(socialToList(userEntity.getUserSocial()));
            result.setUserPicture(userEntity.getUserPicture());
            result.setUserAddress(userEntity.getUserAddress());
            result.setNumberOfPost(blogCount);


            return  result;
        }
        catch(Exception e){
            log.info("Error UserService.getUserProfile()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.getUserProfile()");
        }


    }


    public UserDto getUserProfileById(String userId) throws Exception{

        try {
            log.info("Start UserService.getUserProfile()");

            UserEntity userEntity = userRepository.findById(Integer.valueOf(userId)).get();
            UserDto result = new UserDto();
            int blogCount = userDao.getBlogCount(Integer.parseInt(userId)).get(0).getNumberOfPost();
            result.setUser_id(userEntity.getUserId());
            result.setUsername(userEntity.getUsername());
            result.setUserFName(userEntity.getUserFname());
            result.setUserLName(userEntity.getUserLname());
            result.setUserEmail(userEntity.getUserEmail());
            result.setUserAbout(userEntity.getUserAbout());
            result.setUserSocial(socialToList(userEntity.getUserSocial()));
            result.setUserPicture(userEntity.getUserPicture());
            result.setUserAddress(userEntity.getUserAddress());
            result.setNumberOfPost(blogCount);

            return  result;
        }
        catch(Exception e){
            log.info("Error UserService.getUserProfile()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.getUserProfile()");
        }


    }

    public void editProfileContent(UserDto userDto, Authentication authentication) throws Exception{

        try {
            log.info("Start UserService.editProfileContent()");
            String cUid = jwtService.getUsername(authentication);

            UserEntity userEntity = userRepository.findByCUid(cUid);
            if(userDto.getUserFName() != null){
                userEntity.setUserFname(userDto.getUserFName());
            }

            if(userDto.getUserLName() != null){
                userEntity.setUserLname(userDto.getUserLName());
            }

            if(userDto.getUserSocial() != null){
                userEntity.setUserSocial(Arrays.toString(userDto.getUserSocial()));
            }

            if(userDto.getUserAbout() != null){
                userEntity.setUserAbout(userDto.getUserAbout());
            }


            if(userDto.getDeleteImage() != null){
                if(userDto.getDeleteImage().equals("True")) {
                    userEntity.setUserPicture(null);
                }

            }
            if(userDto.getUserAddress() != null){
                userEntity.setUserAddress(userDto.getUserAddress());
            }

            userRepository.save(userEntity);

        }
        catch(Exception e){
            log.info("Error UserService.editProfileContent()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.editProfileContent()");
        }
    }

    public  void updateProfileImage(MultipartFile image, Authentication authentication) throws Exception{


        try{
            log.info("Start UserService.updateProfileImage()");

            String cUid = jwtService.getUsername(authentication);
            UserEntity userEntity = userRepository.findByCUid(cUid);

            String imageFileName = "user-profile-" + getUserId(cUid);
            String profileUrl = s3Url + imageFileName;
            userEntity.setUserPicture(profileUrl);
            awsS3Service.putImage(image, imageFileName);

        }

        catch(Exception e){
            log.info("Error UserService.updateProfileImage()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End UserService.updateProfileImage()");
        }

    }


    private String[] socialToList(String socialString){
        String trimString = socialString.replaceAll("^\\[|]$", "");
        return trimString.split(",");
    }
}
