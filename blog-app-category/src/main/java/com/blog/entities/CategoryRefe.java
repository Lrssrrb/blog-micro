//package com.blog.entities;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.blog.payloads.PostDto;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Data;
//
//@Entity
//@Data
//public class CategoryRefe {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer categoryId;
//	private String categoryTitle;
//	private String categoryDescription;
//	
////	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	private List<PostDto> posts = new ArrayList<>();
//}
