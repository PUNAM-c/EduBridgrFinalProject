package com.projects.task_management.services.employee;

import java.util.List;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.dto.TaskDTO;

public interface EmployeeService {

	List<TaskDTO> getTaskByUserId(Long id);
    TaskDTO updateTask(Long id,String status);
    TaskDTO getTaskById(Long id);
    
	CommentDTO createComment(Long taskId, Long postedBy, String content);
    List<CommentDTO>getCommentsByTask(Long taskId);
	List<TaskDTO> getTasksById(Long userId);
}
