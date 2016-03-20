package com.crowdsourcing.service;

import java.util.List;

import com.crowdsourcing.exception.CoreException;
import com.crowdsourcing.model.Task;

public interface TaskService {

	public List<Task> getAllTasks() throws CoreException;
	
	public Boolean createTask(Task task) throws CoreException;
	
	public List<Task> getTasksPerUser(String userId) throws CoreException;
		
}
