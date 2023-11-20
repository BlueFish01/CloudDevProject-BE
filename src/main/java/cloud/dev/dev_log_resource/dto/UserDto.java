package cloud.dev.dev_log_resource.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserDto {
    private int user_id;
    private String deleteImage;
    private String username;
    private String userFName;
    private String userLName;
    private String userEmail;
    private String[] userSocial;
    private String userPicture;
    private String userAbout;
    private String userAddress;
    private int numberOfPost;
}
