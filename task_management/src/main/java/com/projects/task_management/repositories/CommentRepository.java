package com.projects.task_management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.task_management.dto.CommentDTO;
import com.projects.task_management.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByTaskId(Long taskId);
   
}
