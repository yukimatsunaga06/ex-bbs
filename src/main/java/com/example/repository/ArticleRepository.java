package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER
		=new BeanPropertyRowMapper<>(Article.class);
	
	public void insert(Article article) {
		SqlParameterSource param=new BeanPropertySqlParameterSource(article);
		String insertSql="INSERT INTO articles(name,content) "
				+ "VALUES(:name,:content)";
		template.update(insertSql, param); //追加
	}
	public List <Article> findAllArticles(){
		String sql="SELECT* FROM articles ORDER BY id DESC";
		List<Article> articleList =template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList; 
	}
	
	
	public void deleteById(int id) {
		String deleteSql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param
			=new MapSqlParameterSource().addValue("id",id);
		template.update(deleteSql, param);
	}
	
	
}
	
	

//http://localhost:8080/ex-bss/2ch