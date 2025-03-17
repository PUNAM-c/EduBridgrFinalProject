package com.projects.task_management.services.admin;

import java.util.List;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.dto.TaskDTO;
import com.projects.task_management.dto.UserDto;

public interface AdminService {
    List<UserDto> getUsers();
    TaskDTO createTask(TaskDTO taskDTO);
    List<TaskDTO> getTask();
     TaskDTO getTaskById(Long id);
     void deleteTask(Long id);
     List<TaskDTO> searchTask(String title);
     TaskDTO updateTask(Long id,TaskDTO taskDTO);
     CommentDTO createComment(Long taskId,Long postedBy,String content);
     List<CommentDTO>getCommentsByTask(Long taskId);
}
