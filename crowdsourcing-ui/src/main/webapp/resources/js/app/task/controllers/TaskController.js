/**
 * worker details Modal window controller
 */
'use strict';
app
		.controller(
				"TaskController",
				[
						'$scope',
						'$rootScope',
						'$cookies',
						'taskService',
						'userService',
						'dialogs',
						'NgTableParams',
						'$filter',
						'localStorageService',
						function($scope, $rootScope, $cookies, taskService, userService,dialogs,
								NgTableParams, $filter, localStorageService) {
							$rootScope.tasks = localStorageService.get('task');
							$scope.task = {};

							// ngTable parameter initilization with data.
							$scope.tableParams = new NgTableParams(
									{
										page : 1, // show first page
										count : 10, // count per page
										filter : {
										// name: 'M' // initial filter
										},
										sorting : {
											number : 'desc' // initial sorting
										}
									},
									{
										getData : function(params) {
											// ajax request to api
											return taskService
													.taskList()
													.then(
															function(data) {
																if (data.data) {
																	var filteredData = params
																			.filter() ? $filter(
																			'filter')
																			(
																					data.data,
																					params
																							.filter())
																			: data.data;
																	var orderedData = params
																			.sorting() ? $filter(
																			'orderBy')
																			(
																					filteredData,
																					params
																							.orderBy())
																			: data.data;
																	params
																			.total(orderedData.length);
																	var offset = params
																			.page() > 1 ? (params
																			.page() - 1)
																			* params
																					.count()
																			: 0;
																	return orderedData
																			.slice(
																					offset,
																					params
																							.count()
																							+ offset);
																}
															});
										}
									});

							$scope.createTask = function() {

								alert("creat task method");
								var userId = $cookies.get("userId");								
								$scope.task.clientId = userId;								
								taskService.saveTask($scope.task);
							};
							
							$scope.getTasksPerUser = function() {

								alert("creat task method");																																						
								taskService.saveTask($cookies.get("userId"));
							};							
						
						}]);