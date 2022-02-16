package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.CommentService;

@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private CommentService commentService;

	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpForm2() {
		return new CommentForm();
	}

	@RequestMapping("")
	public String index(Model model) {

		List<Article> list2 = new ArrayList<>();
		list2 = articleService.findAll2();
		model.addAttribute("list2", list2);
		
				
			
			

		


	return"bbs";

	}

	@RequestMapping("insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());

		articleService.insert(article);

		return "forward:/ex-bbs";

	}

	@RequestMapping("insert-comment")
	public String insertComment(CommentForm commentForm, int articleId) {
		Comment comment = new Comment();
		comment.setName(commentForm.getName());
		comment.setContent(commentForm.getContent());
		comment.setArticleId(articleId);
		commentService.insert(comment);

		return "forward:/ex-bbs";
	}

	@RequestMapping("delete-article")
	public String deleteArticle(Integer id) {
		commentService.deleteByArticleId(id);
		articleService.deleteById(id);

		return "forward:/ex-bbs";
	}

}
