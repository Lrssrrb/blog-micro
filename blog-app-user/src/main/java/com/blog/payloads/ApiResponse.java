package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	String massage;
	Boolean success;
	
	public ApiResponse(String massage) {
		super();
		this.massage = massage;
	}
}
