package cloud.dev.dev_log_resource.service;

import cloud.dev.dev_log_resource.dto.UserDto;
import cloud.dev.dev_log_resource.entity.UserEntity;
import cloud.dev.dev_log_resource.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.amazon.aws.s3Url}")
    private String s3Url;

    public UserDto getUserDetail(String cUid){

        UserEntity user = userRepository.findByCUid(cUid);
        UserDto res = new UserDto();
        res.setUsername(user.getUsername());
        res.setEmail(user.getUserEmail());
        res.setCUid(user.getCUid());
        return res;

    }

    public Integer getUserId(String cUid){
        UserEntity user = userRepository.findByCUid(cUid);
        return user.getUserId();
    }


    public void updateProfile(MultipartFile image, String cUid) throws IOException {

        String imageFileName = "user-profile-" + getUserId(cUid);
        String profileUrl = s3Url + imageFileName;

        awsS3Service.putImage(image, imageFileName);


        UserEntity user = userRepository.findByCUid(cUid);
        user.setUserPicture(profileUrl);
        userRepository.save(user);


    }
}
