package com.learning.hello.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskListDto {
    private long id;
	private String taskname;
}
