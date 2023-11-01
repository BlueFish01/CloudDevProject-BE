package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.dto.PostDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PostDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<PostDto> getLastID(){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM POST ");
        sql.append("ORDER BY post_id DESC ");
        sql.append("LIMIT 1");

        return namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(PostDto.class));
    }
}
