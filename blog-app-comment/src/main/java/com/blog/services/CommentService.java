package com.blog.services;

import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.UserAdmin;
import com.blog.payloads.UserDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,PostDto postDto,UserDto userDto);
	CommentDto editComment(Integer commentId,String newComment,UserAdmin userAdmin,UserDto userDto);
	void deleteComment(Integer commentId,UserAdmin userAdmin,UserDto userDto);
	
}
