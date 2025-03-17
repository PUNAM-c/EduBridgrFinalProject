package com.projects.task_management.services.employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.dto.TaskDTO;
import com.projects.task_management.entities.Comment;
import com.projects.task_management.entities.Task;
import com.projects.task_management.entities.User;
import com.projects.task_management.enums.TaskStatus;
import com.projects.task_management.repositories.CommentRepository;
import com.projects.task_management.repositories.TaskRepository;
import com.projects.task_management.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService{
     private final TaskRepository taskRepository;
     private final UserRepository userRepository;
     private final CommentRepository commentRepository;
	

	public EmployeeServiceImpl(TaskRepository taskRepository, UserRepository userRepository,
			CommentRepository commentRepository) {
		super();
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public List<TaskDTO> getTaskByUserId(Long id) {
		// TODO Auto-generated method stub
		System.out.println("id"+id);
		return taskRepository.findAllByUserId(id).stream().map(Task::getTaskDTO).collect(Collectors.toList());
	}

	@Override
	public TaskDTO updateTask(Long id, String status) {
		Optional<Task> optionaltask = taskRepository.findById(id);
		if (optionaltask.isPresent()) {
			Task existingTask = optionaltask.get();
			
			TaskStatus taskStatus = mapStringToTaskStatus(status);
			existingTask.setTaskStatus(taskStatus);
			return taskRepository.save(existingTask).getTaskDTO();
		}
		return null;
		
	}
	
	private TaskStatus mapStringToTaskStatus(String taskStatus) {
		// TODO Auto-generated method stub
		 return switch (taskStatus) {
	        case "PENDING" -> TaskStatus.PENDING;
	        case "COMPLETED" -> TaskStatus.COMPLETED;
	        case "INPROGRESS" -> TaskStatus.INPROGRESS;
	        case "DEFERRED" -> TaskStatus.DEFERED;
	        default -> TaskStatus.CANCELLED;
	    };	}

//	

//	@Override
//	public TaskDTO getTaskById(Long id) {
////	    Optional<Task> taskOptional = taskRepository.findById(id);
////	    
////	    if (taskOptional.isPresent()) {
////	        System.out.println("Task found: " + taskOptional.get()); // Debugging statement
////	        return taskOptional.get().getTaskDTO();
////	    } else {
////	        System.out.println("Task not found for ID: " + id); // Debugging statement
////	        return null;
////	    }
//		 return taskRepository.findTaskByIdWithUser(id)
//		            .map(Task::getTaskDTO)
//		            .orElse(null);
//	}
	
	@Override
	public List<TaskDTO> getTasksById(Long userId) {
	    return taskRepository.findTasksByUserId(userId)
	            .stream()
	            .map(Task::getTaskDTO)
	            .collect(Collectors.toList());
	}


	
	
	@Override
	public CommentDTO createComment(Long taskId, Long postedBy, String content) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		Optional<User> optionalUser = userRepository.findById(postedBy);
		if(optionalUser.isPresent() && optionalUser.isPresent()) {
			 Comment comment = new Comment();
			 comment.setContent(content);
			 comment.setCreatedAt(new Date());
			 comment.setUser(optionalUser.get());
			 comment.setTask(optionalTask.get());
			 return commentRepository.save(comment).getCommentDTO();
		}
		throw new EntityNotFoundException("task or User not Found..");
	}

	@Override
	public List<CommentDTO> getCommentsByTask(Long taskId) {
		// TODO Auto-generated method stub
		
		return commentRepository.findAllByTaskId(taskId).stream().map(Comment::getCommentDTO).collect(Collectors.toList());
	}

	@Override
	public TaskDTO getTaskById(Long id) {
		// TODO Auto-generated method stub
		Task task = taskRepository.getById(id);
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(task.getId());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setDueDate(task.getDueDate());
		taskDTO.setEmployeeName(task.getUser().getName());
		taskDTO.setEmployeeId(task.getUser().getId());
		taskDTO.setPriority(task.getPriority());
		taskDTO.setTaskStatus(task.getTaskStatus());
		taskDTO.setTitle(task.getTitle());
		
		
		return taskDTO;
	}
     
}
