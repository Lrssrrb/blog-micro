package com.blog.repositries;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(Integer user);
	List<Post> findByCategory(Integer user);
	Page<Post> findByUser(Integer user,PageRequest pageble);
	Page<Post> findByCategory(Integer user,PageRequest pageble);
	List<Post> findByTitleContaining(String title);
	
}
