package com.blog.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.blog.exeption.UserExeption;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.UserAdmin;
import com.blog.payloads.UserDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RestTemplate restTemplate;

	private UserAdmin user;
	
	private UserDto userDto;
	
	private PostDto postDto;
	
	private void getUser(Map<String, List<String>> jwt,int postId) {
		
		if(user == null) {
			try {
				Object header = jwt.get("authorization");
				String h = header.toString();
				jwt.clear();
				List<String> list = new ArrayList<>();
				list.add(h);
				jwt.put("authorization", list);
				
				MultiValueMapAdapter<String, String> multiValueMapAdapter = new MultiValueMapAdapter<>(jwt);
				URI uri = new URI("/api/users/isAdmin");
				RequestEntity<String> requestEntity = new RequestEntity<>("",multiValueMapAdapter,HttpMethod.GET,uri);
				ResponseEntity<UserAdmin> exchange = restTemplate.exchange(requestEntity, UserAdmin.class);

				user = exchange.getBody();

				userDto = restTemplate.getForObject("/api/users/getByEmail",UserDto.class, user.getUser());
				
				if(postId < 0)
					postDto = restTemplate.getForObject("/api/posts/"+postId, PostDto.class);
			} catch (Exception e) {
					throw new UserExeption("Service unavalable.");
			}
		}
		else if(user.getValidity().isBefore(LocalDateTime.now())){
			throw new UserExeption("Token Expiered");
		}
	}

	
	@PostMapping("post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId,@RequestHeader Map<String, List<String>> jwt){
		
		getUser(jwt, postId);
		
		
		CommentDto createComment = this.commentService.createComment(commentDto, postDto,userDto);
		
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}
	
	@PutMapping("comments/{commentId}")
	public ResponseEntity<CommentDto> editComment(@RequestBody String commentDto,@PathVariable Integer commentId,@RequestHeader Map<String, List<String>> jwt){
		
		getUser(jwt, -1);
		
		if(user.getUser()==null)
			throw new UserExeption("Please login first.");
		
		CommentDto createComment = this.commentService.editComment(commentId,commentDto,user,userDto);
		
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId,@RequestHeader Map<String, List<String>> jwt){
				
		getUser(jwt,-1);
		
		if(user.getUser()==null)
			throw new UserExeption("Please login first.");
		
		this.commentService.deleteComment(commentId,user,userDto);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successFully.",true), HttpStatus.OK);
	}
}
