package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<BlogEntity, Integer> {
}
