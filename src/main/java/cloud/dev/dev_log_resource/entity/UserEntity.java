package cloud.dev.dev_log_resource.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "c_uid")
    private String cUid;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "user_fname")
    private String userFname;

    @Basic
    @Column(name = "user_lname")
    private String userLname;

    @Basic
    @Column(name = "user_email")
    private String userEmail;

    @Basic
    @Column(name = "user_social")
    private String userSocial;

    @Basic
    @Column(name = "user_picture")
    private String userPicture;

    @Basic
    @Column(name = "user_about")
    private String userAbout;

    @Basic
    @Column(name = "create_date")
    private Date createDate;

    @Basic
    @Column(name = "user_status")
    private String userStatus;
}
