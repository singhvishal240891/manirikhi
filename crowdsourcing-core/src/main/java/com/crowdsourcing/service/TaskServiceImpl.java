package com.crowdsourcing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.crowdsourcing.dao.ClientDAO;
import com.crowdsourcing.dao.TaskDAO;
import com.crowdsourcing.dao.WorkerDAO;
import com.crowdsourcing.exception.CoreException;
import com.crowdsourcing.model.Client;
import com.crowdsourcing.model.Task;
import com.crowdsourcing.model.Worker;

@Service("taskService")
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDAO taskDAO;
	@Autowired
	private ClientDAO clientDAO;
	@Autowired
	private WorkerDAO workerDAO;
	
	@Override
	public List<Task> getAllTasks() throws CoreException {		
		return taskDAO.getAll();
	}

	@Override
	public Boolean createTask(Task task) throws CoreException {
		return taskDAO.create(task);
	}
	
	@Override
	public List<Task> getTasksPerUser(String userId) throws CoreException {
		
		Client client = clientDAO.getById(userId);
		List<String> taskIds = new ArrayList<>();
		if(client!=null){
			taskIds = client.getTaskIds();	
		}
		else{
			Worker worker = workerDAO.getById(userId);
			if(worker!=null){
				taskIds = worker.getTaskIds();	
			}			
		}		
								
	    Query query = new Query(Criteria.where("taskId").in(taskIds));						
		
		return taskDAO.find(query);
	}

}
