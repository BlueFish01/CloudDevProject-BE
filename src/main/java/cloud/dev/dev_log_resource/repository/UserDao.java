package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<UserDto> getBlogCount(int userId){
        StringBuffer sql = new StringBuffer();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        sql.append("SELECT ");
        sql.append("COUNT(blog_id) AS number_of_post ");
        sql.append("FROM ");
        sql.append("BLOG ");
        sql.append("WHERE ");
        sql.append("blog_owner = :userId");
        parameters.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql.toString(),parameters, new BeanPropertyRowMapper(UserDto.class));
    }

}
