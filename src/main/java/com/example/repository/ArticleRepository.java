package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER=new BeanPropertyRowMapper<>(Article.class);
	
	/**Articlesテーブル全件取得
	 * @return　Articleリスト
	 */
	public List<Article> findAll(){
		System.out.println("repository");
		String sql="SELECT * FROM articles ORDER BY id desc;";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}
	
	public void insert(Article article) {
		String sql="INSERT INTO articles(name,content)VALUES (:name,:content);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",article.getContent());
		template.update(sql, param);
	}

	public void deleteById(Integer id) {
		String sql="DELETE FROM articles WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		template.update(sql, param);
		
	}
}
