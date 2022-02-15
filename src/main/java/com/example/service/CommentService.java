package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Comment;
import com.example.repository.CommentRepository;

@Service
@Transactional
public class CommentService {
	
	@Autowired
	private CommentRepository repository;
	
	public List<Comment> findByArticleId(int articleId){
		List<Comment> list=repository.findByArticleId(articleId);
		return list;
	}
	
	public void insert(Comment comment) {
		repository.insert(comment);
	}
	
	public void deleteByArticleId(int articleId) {
		repository.deleteByArticleId(articleId);
	}

}
