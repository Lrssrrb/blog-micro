package com.blog.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.exeption.ResourceNotFoundException;
import com.blog.exeption.UserExeption;
import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.UserAdmin;
import com.blog.payloads.UserDto;
import com.blog.repositries.CommentRepo;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

//	@Autowired
//	private PostRepo postRepo;
	
//	@Autowired
//	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, PostDto postDto,UserDto userDto) {
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(postDto.getId());
		comment.setUser(userDto.getId());
		
		this.commentRepo.save(comment);
		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public CommentDto editComment(Integer commentId, String newComment,UserAdmin userAdmin,UserDto userDto) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
	
		Optional<Comment> commentOptional = commentRepo.findById(commentId);
		if (commentOptional.isEmpty()) {
			throw new UserExeption("No comment found by id : "+commentId);
		}
		if(!userAdmin.isAdmin() || commentOptional.get().getUser() != userDto.getId())
			throw new UserExeption("Only Creater can edit his/her comment.");
		
		comment.setContent(newComment);
		Comment save = this.commentRepo.save(comment);
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId,UserAdmin userAdmin,UserDto userDto) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
		
		if(!userAdmin.isAdmin() || comment.getUser() != userDto.getId())
			throw new UserExeption("Only Creater can delete his/her comment.");
		
		this.commentRepo.delete(comment);
	}

}
