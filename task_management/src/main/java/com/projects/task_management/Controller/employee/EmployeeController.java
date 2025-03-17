package com.projects.task_management.Controller.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.dto.TaskDTO;
import com.projects.task_management.services.employee.EmployeeService;

import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<?> getTaskByUserId(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.getTaskByUserId(id));
	}

	@GetMapping("/task/by/{id}/{status}")
	public ResponseEntity<?> updateTask(@PathVariable Long id, @PathVariable String status) {
		TaskDTO updatedTaskDTO = employeeService.updateTask(id, status);
		if (updatedTaskDTO == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedTaskDTO);

	}
	
	@GetMapping("/tasks/by/{id}")
	public ResponseEntity<?> getTasksById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.getTasksById(id));
	}
	
	@GetMapping("/task/by/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.getTaskById(id));
	}
	
  
	 @PostMapping("/task/comment")
	    public ResponseEntity<?> createComment(@RequestParam Long taskId,@RequestParam Long postedBy,@RequestBody String content){
	     CommentDTO createdCommentDTO= employeeService.createComment(taskId, postedBy,content);
	      if(createdCommentDTO == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	      return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);
	      
	    }
	 
	 @GetMapping("/task/{taskId}/comments")
		public ResponseEntity<?> getCommentByTask(@PathVariable Long taskId) {
			return ResponseEntity.ok(employeeService.getCommentsByTask(taskId));
		}
}
