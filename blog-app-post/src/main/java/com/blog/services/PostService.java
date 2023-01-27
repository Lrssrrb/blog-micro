package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponce;
import com.blog.payloads.UserAdmin;
import com.blog.payloads.UserDto;

public interface PostService {

	PostDto createPost(PostDto postDto,CategoryDto category,UserDto userDto);
//	PostDto createPost(String );
//	public PostDto createPostwithImg(PostDto postDto, Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId,UserAdmin user,UserDto userDto);
	void deletePost(Integer postId,UserAdmin user,UserDto userDto);
	List<PostDto> getAllPost();
	PostDto getPostById(Integer postId);
	List<PostDto> getPostsByCategory(Integer category);
	List<PostDto> getPostsByUser(Integer userId);
//	PostResponce getPagedPost(int page_size,int page_number);
//	PostResponce getPagedPostByUser(int userId,int page_size,int page_number);
//	PostResponce getPagedPostByCategory(int categoryId,int page_size,int page_number);
//	PostResponce getAllPaged$SortedPosts(int pageSize, int pageNumber, String sortBy,Boolean assending);
	PostResponce getEverything(int pageSize, int pageNumber, String sortBy,String sortDir);
	List<PostDto> searchPost(String keyword);
//	PostDto createPostwithImg(PostDto postDto, Integer userId, Integer categoryId,MultipartFile image) throws IOException;
//	PostDto createPost(@Valid PostDto postDto, Integer categoryId, String token);
	
}
