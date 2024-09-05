package com.learning.hello.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListDto {
    private long id;
	private String name;
	private String email;
	private String password;
}
