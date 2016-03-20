/**
 * Task services CRUD ajax calls
 */

  "use strict";
        
    app.service('taskService', ['$http', function ($http){       	
        	this.saveTask = function(task) {        		
        			return $http.post('task/create.ajax', 
                    		task);        		        		                        
        	};
        	//get all tasks
        	this.taskList = function(){
        		return $http.get('tasks.ajax');
        	};
        	//get task per user
        	this.getTasksPerUser = function(userId){
        		return $http.post('tasks.ajax',{params:{ userId : userId }});
        	};        	        
        	this.deleteTask = function(TaskId){
        		return $http.get('deleteTask.ajax',{params:{ TaskId : TaskId }});
        	};
        	this.updateTask = function(Task) {
                return $http.put('updateTask.ajax', 
                		Task);         		
        	};
         }]);           
