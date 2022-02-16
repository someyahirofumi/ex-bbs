package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

@Repository
public class ArticleRepository {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
//	private static Article article;
//	private static int id;
//	private static int preId= 0 ;
//	private static List<Comment> commentList=null;
//	private static List<Article>articleList;
//	
//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER=(rs,i)->{
//		
//		id=rs.getInt("id");
//		
//		if(id != preId) {
//			article.setId(rs.getInt("id"));
//			article.setName(rs.getString("name"));
//			article.setContent(rs.getString("content"));
//			
//			commentList= new ArrayList<>();
//			article.setCommentList(commentList);
//			articleList.add(article);
//		}
//		
//		Comment comment = new Comment();
//		comment.setName(rs.getString("com_name"));
//		comment.setContent(rs.getString("com_content"));
//		comment.setArticleId(rs.getInt("article_id"));
//		comment.setId(rs.getInt("com_id"));
//	commentList.add(comment);
//		
//		
//		preId=id;
//		return article;
//	};
	
	private static int id;
	private static int preId=0;
	private static List<Comment> commentList=null;
	private static final ResultSetExtractor<List<Article>> ArticleResultSetExtractor=(rs)->{
		
		List<Article> articleList= new ArrayList<>();
	
		while(rs.next()) {
			id=rs.getInt("id");
			if(id != preId) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList= new ArrayList<>();
				article.setCommentList(commentList);
				articleList.add(article);
				
			}
			if(rs.getInt("com_id") !=0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			preId = id;
		}
		return articleList;
		
	};
	/**Articlesテーブル全件取得
	 * @return　Articleリスト
	 */
	
	
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
	
	public List<Article> findAll2(){
		String sql ="select a.id as id,a.name as name,a.content as content,c.id as com_id,c.name as com_name,c.content as com_content,c.article_id as article_id"
				+ " from "
				+ " articles as a"
				+ " left outer join"
				+ " comments as c"
				+ " on"
				+ " a.id=c.article_id"
				+ " order by"
				+ " id desc;";
				return template.query(sql, ArticleResultSetExtractor);
				
				
	}
	
	
}
