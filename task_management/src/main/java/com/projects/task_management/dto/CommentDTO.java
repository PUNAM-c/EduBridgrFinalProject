package com.projects.task_management.dto;

import java.util.Date;

public class CommentDTO {
private long id;
	
	private String content;
	private Date createdAt;
	private Long postedUserId;
	private String postedName;
	private Long taskId;
	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}
	public CommentDTO(long id, String content, Date createdAt, Long postedUserId, String postedName, Long taskId) {
		super();
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.postedUserId = postedUserId;
		this.postedName = postedName;
		this.taskId = taskId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getPostedUserId() {
		return postedUserId;
	}
	public void setPostedUserId(Long postedUserId) {
		this.postedUserId = postedUserId;
	}
	public String getPostedName() {
		return postedName;
	}
	public void setPostedName(String postedName) {
		this.postedName = postedName;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
}
