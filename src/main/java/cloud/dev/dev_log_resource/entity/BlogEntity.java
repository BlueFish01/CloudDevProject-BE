package cloud.dev.dev_log_resource.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "BLOG")
public class BlogEntity {
    @Id
    @Column(name = "blog_id")
    private Integer blogId;

    @Basic
    @Column(name = "blog_owner")
    private Integer blogOwner;

    @Basic
    @Column(name = "blog_title")
    private String blogTitle;

    @Basic
    @Column(name = "blog_view")
    private Integer blogView;

    @Basic
    @Column(name = "blog_create_date", updatable = false)
    private Timestamp blogCreateDate;

    @Basic
    @Column(name = "blog_edit_date", insertable = false)
    private Timestamp blogEditDate;


    @PrePersist
    public void prePersist() {
        this.blogCreateDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.blogEditDate = new Timestamp(System.currentTimeMillis());
    }


}
