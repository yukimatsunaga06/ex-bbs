package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/2ch")
public class ArticleController {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	@RequestMapping("")
	public String findAll(Model model) {
		List<Article> articleList = articleRepository.findAllArticles();
		for(Article article : articleList) {
			article.setCommentList(commentRepository.findAllComments(article.getId()));
		}
			model.addAttribute("articleList", articleList);
		return "board";
	}
	
	@RequestMapping("/insertArticle")
		public String insertArticle(ArticleForm form) {
			Article article = new Article();
			BeanUtils.copyProperties(form,article);
			articleRepository.insert(article);
			return "redirect:/2ch"; //redirectで二重送信防止
		}
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form,comment);
		comment.setArticleId(form.getIntArticleId());
		commentRepository.insert(comment);
		return "redirect:/2ch"; //redirectで二重送信防止
	}
	

	@RequestMapping("/deleatecomment")
	public String deleteComment(Integer articleId){
		articleRepository.deleteById(articleId);
		commentRepository.deleteById(articleId);
		return "redirect:/2ch";
	}
	
	
}
