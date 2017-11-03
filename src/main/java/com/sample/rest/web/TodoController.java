package com.sample.rest.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sample.rest.service.TodoService;
import com.sample.rest.vo.Todo;

@RestController
// @Controller + @Responsebody의 역할 담당
public class TodoController {

	@Autowired
	TodoService todoService;
	
	@GetMapping("/todos")
	public List<Todo> getTodos() {
		return todoService.getTodos();
	}
	
	@GetMapping(value="/todos/{no}")
	public Todo getTodo(@PathVariable("no") int no) {
		return todoService.getTodo(no);
	}
	
	@PostMapping("/todos")
	public List<Todo> addTodo(@RequestBody Todo todo) {
		// System.out.println(todo); 
		// todo가 null값이 나옴 b/c request.getParameter가 아닌 json형식으로 전송되서
		// so, 객체 앞에 @RequestBody 필요
		todoService.addTodo(todo);
		return todoService.getTodos();
	}
	@DeleteMapping("/todos/{no}")
	public Todo deleteTodo(@PathVariable("no")int no) {
		Todo todo = todoService.getTodo(no);
		todoService.deleteTodo(no);
		return todo;
	}
	@PutMapping("/todos")
	public List<Todo> updateTodo(@RequestBody Todo todo) {
		todoService.updateTodo(todo);
		return todoService.getTodos();
	}
}
