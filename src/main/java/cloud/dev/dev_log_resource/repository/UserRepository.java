package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.cUid = :cUid")
    UserEntity findByCUid(String cUid);



}
