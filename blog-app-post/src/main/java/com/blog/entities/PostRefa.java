//package com.blog.entities;
//
//import java.util.Date;
//import java.util.List;
//
//import com.blog.payloads.CategoryDto;
//import com.blog.payloads.CommentDto;
//import com.blog.payloads.UserDto;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Data;
//
//@Entity
//@Data
//public class PostRefa {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	private String title;
//	private String content;
//	private String imageName;
//	private Date addedDate;
//	
////	@ManyToOne
////	@JoinColumn(name = "category_id")
//	private CategoryDto category;
//	
////	@ManyToOne
//	private UserDto user;
//	
////	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
//	private List<CommentDto> comments;
//
//}
