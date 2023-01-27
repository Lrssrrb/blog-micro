package com.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blog.entities.Post;
import com.blog.exeption.ResourceNotFoundException;
import com.blog.exeption.UserExeption;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponce;
import com.blog.payloads.UserAdmin;
import com.blog.payloads.UserDto;
import com.blog.repositries.PostRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public PostDto createPost(PostDto postDto, CategoryDto category,UserDto userDto) {
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		
		post.setUser(userDto.getId());
		post.setCategory(category.getCategoryId());
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}
	

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId,UserAdmin user,UserDto userDto) {
		

		Post postGoted = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		if(!user.isAdmin() || userDto.getId() != postGoted.getUser())
			throw new UserExeption("Only Creater can delete his/her Post.");
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		if(post.getContent() == null) {
			post.setContent(postGoted.getContent());
		}
		if(post.getImageName() == null) {
			post.setImageName(postGoted.getImageName());
		}
		if(post.getTitle() == null) {
			post.setTitle(postGoted.getTitle());
		}
		
		post.setId(postId);
		post.setCategory(postGoted.getCategory());
		post.setUser(postGoted.getUser());
		post.setAddedDate(postGoted.getAddedDate());
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId,UserAdmin user,UserDto userDto) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		if(!user.isAdmin() || userDto.getId() != post.getUser())
			throw new UserExeption("Only Creater can delete his/her Post.");
		
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		
		List<Post> pagePost = this.postRepo.findAll();
		List<PostDto> postDtos = pagePost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer category) {
		
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer user) {
//		User user = this.userRepo.findById(userId)
//				.orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		
//		UserDto userDto = restTemplate.getForObject("http://localhost:8001/api/users/"+userId,UserDto.class);
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	/*
	{
	@Override
	public PostResponce getPagedPost(int page_size,int page_Number) {
		PageRequest pageble = PageRequest.of(page_Number, page_size);
		Page<Post> pagePost = this.postRepo.findAll(pageble);
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postDtos = allpost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElement(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setFirstPage(pagePost.isFirst());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}
	
	@Override
	public PostResponce getPagedPostByUser(int userId,int page_size,int page_number) {
		PageRequest pageble = PageRequest.of(page_number, page_size);
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		
		Page<Post> pagePost = this.postRepo.findByUser(user,pageble);
		
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postDtos = allpost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElement(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setFirstPage(pagePost.isFirst());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}

	@Override
	public PostResponce getPagedPostByCategory(int categoryId, int page_size, int page_number) {
		
		PageRequest pageble = PageRequest.of(page_number, page_size);
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		Page<Post> pagePost = this.postRepo.findByCategory(category,pageble);
		
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postDtos = allpost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElement(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setFirstPage(pagePost.isFirst());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}

	@Override
	public PostResponce getAllPaged$SortedPosts(int pageSize, int pageNumber, String sortBy,Boolean assending) {
		
		PageRequest pageble = null;
		if(assending)
			pageble = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		else
			pageble = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
		Page<Post> pagePost = this.postRepo.findAll(pageble);
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postDtos = allpost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElement(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setFirstPage(pagePost.isFirst());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}
	}
	*/
	
	@Override
	public PostResponce getEverything(int pageSize, int pageNumber, String sortBy,String sortDir) {
		PageRequest pageble = null;
		if(sortDir.equalsIgnoreCase("asc"))
			pageble = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).ascending());
		else
			pageble = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
		Page<Post> pagePost = this.postRepo.findAll(pageble);
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postDtos = allpost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElement(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setFirstPage(pagePost.isFirst());
		postResponce.setLastPage(pagePost.isLast());
		return postResponce;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	
//	private boolean isAdmin() {
//		
//		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		
//		for (Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); iterator.hasNext();) {
//			GrantedAuthority grantedAuthority = (GrantedAuthority) iterator.next();
//			if(grantedAuthority.getAuthority().equals("ADMIN_USER"))
//				return true;
//		}
//		
//		return false;
//	}
	
}
