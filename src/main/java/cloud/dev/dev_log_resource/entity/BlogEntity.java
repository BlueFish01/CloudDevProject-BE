package cloud.dev.dev_log_resource.entity;

import lombok.Data;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "POST")
public class PostEntity {
    @Id
    @Column(name = "post_id")
    private Integer postId;

    @Basic
    @Column(name = "post_owner")
    private Integer postOwner;

    @Basic
    @Column(name = "post_title")
    private String postTitle;

    @Basic
    @Column(name = "post_create_date", updatable = false)
    private Timestamp postCreateDate;

    @Basic
    @Column(name = "post_edit_date", insertable = false)
    private Timestamp postEditDate;

    @PrePersist
    public void prePersist() {
        this.postCreateDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.postEditDate = new Timestamp(System.currentTimeMillis());
    }


}
