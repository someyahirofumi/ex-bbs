package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
public class CommentRepository {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER=new BeanPropertyRowMapper<>(Comment.class);

	public List<Comment> findByArticleId(int articleId){
		String sql="SELECT * FROM comments WHERE article_id = :articleId　ORDER BY id desc;";
		SqlParameterSource param =new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment>list= template.query(sql, param,COMMENT_ROW_MAPPER);
		return list;
	}
	
	public void insert(Comment comment) {
		String sql="INSERT INTO comments(name,content,article_id) VALUES(:name,:content,:articleId);";
		SqlParameterSource param=new MapSqlParameterSource().addValue("name", comment.getName()).addValue("content", comment.getContent()).addValue("articleId", comment.getArticleId());
		template.update(sql, param);
	}
	
}
