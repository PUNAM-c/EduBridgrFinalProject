package com.projects.task_management.services.admin;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.dto.TaskDTO;
import com.projects.task_management.dto.UserDto;
import com.projects.task_management.entities.Comment;
import com.projects.task_management.entities.Task;
import com.projects.task_management.entities.User;
import com.projects.task_management.enums.TaskStatus;
import com.projects.task_management.enums.UserRole;
import com.projects.task_management.repositories.CommentRepository;
import com.projects.task_management.repositories.TaskRepository;
import com.projects.task_management.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {
	private final UserRepository userRepository;

	private final TaskRepository taskRepository;

	public AdminServiceImpl(UserRepository userRepository, TaskRepository taskRepository,
			CommentRepository commentRepository) {
		super();
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
		this.commentRepository = commentRepository;
	}

	private final CommentRepository commentRepository;
	
	@Override
	public List<UserDto> getUsers() {
		return userRepository.findAll().stream().filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
				.map(User::getUserDto).collect(Collectors.toList());
	}

	@Override
	public TaskDTO createTask(TaskDTO taskDTO) {
		Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());
		if (optionalUser.isPresent()) {
			Task task = new Task();
			task.setTitle(taskDTO.getTitle());
			task.setDescription(taskDTO.getDescription());
			task.setPriority(taskDTO.getPriority());
			task.setDueDate(taskDTO.getDueDate());
			task.setTaskStatus(TaskStatus.INPROGRESS);
			task.setUser(optionalUser.get());
			return taskRepository.save(task).getTaskDTO();
		}
		return null;

	}

	@Override
	public List<TaskDTO> getTask() {
		// TODO Auto-generated method stub
		return taskRepository.findAll().stream().map(Task::getTaskDTO).collect(Collectors.toList());
	}

	@Override
	public TaskDTO getTaskById(Long id) {
		Optional<Task> optionalTask = taskRepository.findById(id);
		return optionalTask.map(Task::getTaskDTO).orElse(null);
	}

	@Override
	public void deleteTask(Long id) {
		// TODO Auto-generated method stub
		taskRepository.deleteById(id);

	}

	@Override
	public List<TaskDTO> searchTask(String title) {

		return taskRepository.findAllBytitleContaining(title).stream().map(Task::getTaskDTO)
				.collect(Collectors.toList());
	}

	@Override
	public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
		Optional<Task> optionaltask = taskRepository.findById(id);
		Optional<User> optionaluser = userRepository.findById(taskDTO.getEmployeeId());
		if (optionaltask.isPresent() && optionaluser.isPresent()) {
			Task existingTask = optionaltask.get();
			existingTask.setTitle(taskDTO.getTitle());
			existingTask.setDescription(taskDTO.getDescription());
			existingTask.setDueDate(taskDTO.getDueDate());
			existingTask.setPriority(taskDTO.getPriority());
			existingTask.setUser(optionaluser.get());
			TaskStatus taskStatus = mapStringToTaskStatus( String.valueOf(taskDTO.getTaskStatus()));
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
}
