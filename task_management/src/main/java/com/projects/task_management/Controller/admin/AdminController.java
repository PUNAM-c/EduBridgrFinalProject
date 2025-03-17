package com.projects.task_management.Controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.dto.TaskDTO;
import com.projects.task_management.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")

public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
		super();
		this.adminService = adminService;
	}

	@GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO createTaskDTO = adminService.createTask(taskDTO);
        if(createTaskDTO == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskDTO);
    }
    
    @GetMapping("/task")
    public ResponseEntity<?> getTask(){
        return ResponseEntity.ok(adminService.getTask());
    }
    
    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTaskById(id));
    }
    
    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
    	adminService.deleteTask(id);
    	return ResponseEntity.ok(null);
    }
    @GetMapping("/task/search/{title}")
    public ResponseEntity<?> searchTask(@PathVariable String title){
        return ResponseEntity.ok(adminService.searchTask(title));
    }
    
    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
      TaskDTO updatedTaskDTO= adminService.updateTask(id, taskDTO);
      if(updatedTaskDTO == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      return ResponseEntity.status(HttpStatus.CREATED).body(updatedTaskDTO);
      
    }
    
    @PostMapping("/task/comment")
    public ResponseEntity<?> createComment(@RequestParam Long taskId,@RequestParam Long postedBy,@RequestBody String content){
     CommentDTO createdCommentDTO= adminService.createComment(taskId, postedBy,content);
      if(createdCommentDTO == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);
      
    }
    @GetMapping("/task/{taskId}/comments")
	public ResponseEntity<?> getCommentByTask(@PathVariable Long taskId) {
		return ResponseEntity.ok(adminService.getCommentsByTask(taskId));
	}
    
}
