package com.projects.task_management.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projects.task_management.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllBytitleContaining(String title);

	List<Task> findAllByUserId(Long id);

	@Query("SELECT t FROM Task t WHERE t.user.id = :userId")
	List<Task> findTasksByUserId(@Param("userId") Long userId);

	

}
