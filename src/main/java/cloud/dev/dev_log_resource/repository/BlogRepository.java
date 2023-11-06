package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.entity.BlogEntity;
import cloud.dev.dev_log_resource.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    @Query("SELECT u FROM BlogEntity u WHERE u.blogId = :blogId")
    BlogEntity getBlogOwnerId(int blogId);
}
