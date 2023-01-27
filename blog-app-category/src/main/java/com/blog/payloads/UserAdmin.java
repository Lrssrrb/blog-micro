package com.blog.payloads;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdmin {

	String user;
	boolean isAdmin;
	LocalDateTime validity;
}
