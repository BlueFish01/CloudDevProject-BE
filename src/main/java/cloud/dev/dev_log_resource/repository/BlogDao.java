package cloud.dev.dev_log_resource.repository;

import cloud.dev.dev_log_resource.dto.BlogDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BlogDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BlogDto> getLastID(){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM BLOG ");
        sql.append("ORDER BY blog_id DESC ");
        sql.append("LIMIT 1");

        return namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(BlogDto.class));
    }

    public List<BlogDto> getBlogByLatest(int limit, Integer userId) {
        StringBuffer sql = new StringBuffer();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        sql.append("SELECT ");
        sql.append("blog_id, ");
        sql.append("blog_owner as owner_id, ");
        sql.append("blog_title, ");
        sql.append("blog_view, ");
        sql.append("blog_create_date ");
        sql.append("FROM ");
        sql.append("BLOG ");
        if (userId != null){
            sql.append("WHERE ");
            sql.append(" blog_owner = :userId ");
            parameters.addValue("userId", userId);
        }
        sql.append("ORDER BY blog_create_date DESC");
        if (limit > 0){
            sql.append(" LIMIT :limit");
            parameters.addValue("limit", limit);
        }
        return namedParameterJdbcTemplate.query(sql.toString(),parameters, new BeanPropertyRowMapper(BlogDto.class));
    }

    public List<BlogDto> getBlogByPopular(int limit, Integer userId) {
        StringBuffer sql = new StringBuffer();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        sql.append("SELECT ");
        sql.append("blog_id, ");
        sql.append("blog_owner as owner_id, ");
        sql.append("blog_title, ");
        sql.append("blog_view, ");
        sql.append("blog_create_date ");
        sql.append("FROM ");
        sql.append("BLOG ");
        if (userId != null){
            sql.append("WHERE ");
            sql.append(" blog_owner = :userId ");
            parameters.addValue("userId", userId);
        }
        sql.append("ORDER BY blog_view DESC");
        if (limit > 0){
            sql.append(" LIMIT :limit");
            parameters.addValue("limit", limit);
        }
        return namedParameterJdbcTemplate.query(sql.toString(),parameters, new BeanPropertyRowMapper(BlogDto.class));

    }

    public List<BlogDto> getBlogByOldest(int limit, Integer userId) {
        StringBuffer sql = new StringBuffer();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        sql.append("SELECT ");
        sql.append("blog_id, ");
        sql.append("blog_owner as owner_id, ");
        sql.append("blog_title, ");
        sql.append("blog_view, ");
        sql.append("blog_create_date ");
        sql.append("FROM ");
        sql.append("BLOG ");
        if (userId != null){
            sql.append("WHERE ");
            sql.append(" blog_owner = :userId ");
            parameters.addValue("userId", userId);
        }
        sql.append("ORDER BY blog_create_date ASC");
        if (limit > 0){
            sql.append(" LIMIT :limit");
            parameters.addValue("limit", limit);
        }
        return namedParameterJdbcTemplate.query(sql.toString(),parameters, new BeanPropertyRowMapper(BlogDto.class));

    }
}
