package com.crowdsourcing.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.crowdsourcing.dao.WorkerDAO;
import com.crowdsourcing.dao.TaskDAO;
import com.crowdsourcing.exception.CoreException;
import com.crowdsourcing.model.Client;
import com.crowdsourcing.model.Task;
import com.crowdsourcing.model.Worker;


@Service("workerService")
public class WorkerServiceImpl implements WorkerService{
	
	@Autowired
	private WorkerDAO workerDAO;
	@Autowired
	private TaskDAO taskDAO;

	
	@Override
	public Boolean updateWroker(Worker worker) throws CoreException {
		
		if(worker == null || (worker!=null && worker.getWorkerId()==null)){
			return false;
		}				
		
		return workerDAO.create(worker);		
	}

	@Override
	public Worker getWorkerDetails(String workerId) throws CoreException {		
		return workerDAO.getById(workerId);
	}
	
	
	@Override
	public Boolean acceptTask(String taskId,String workerId) throws CoreException{
		
		Task task = taskDAO.getById(taskId);
		task.setTaskAssigned(true);
		taskDAO.create(task);
		
		Worker worker = workerDAO.getById(workerId);
		worker.getTaskIds().add(task.getTaskId());
		return workerDAO.create(worker);
		
	}

	@Override
	public Boolean signUp(Worker worker) throws CoreException {
		return workerDAO.create(worker);
	}

	@Override
	public String signIn(String email, String password) throws CoreException {
		
		String workerId = "";
		Query searchUserQuery = new Query(Criteria.where("details.email").is(email));
	    searchUserQuery.addCriteria(Criteria.where("details.password").is(password));		   
	      
	    List<Worker> workers = workerDAO.find(searchUserQuery);
	    if(!CollectionUtils.isEmpty(workers)){
	    	workerId = workers.get(0).getWorkerId();
	    }
	    
	    return workerId;
	}

	@Override
	public Boolean markTaskCompleted(String taskId,String workerId) throws CoreException{
		
		Worker worker = workerDAO.getById(workerId);
		List<String> taskIds = worker.getTaskIds();
		for(String task : taskIds){
			if(task.equals(taskId)){
				Task existingTask = taskDAO.getById(taskId);
				existingTask.setTaskCompleted(true);
				taskDAO.create(existingTask);
				break;
			}
		}
		//increment worker experience
		worker.setWorkExperience(worker.getWorkExperience()+1);
		return workerDAO.create(worker);
	}		

	@Override
	public List<Worker> getAllWorkers() throws CoreException {		
		return workerDAO.getAll();
	}	

	@Override
	public Worker getById(String id) throws CoreException {
		return workerDAO.getById(id);
	}
	
}
