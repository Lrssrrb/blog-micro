package com.blog.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	
//	@ManyToOne
//	@JoinColumn(name = "category_id")
	private Integer category;
	
//	@ManyToOne
	private Integer user;
	
//	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Integer> comments;

}
