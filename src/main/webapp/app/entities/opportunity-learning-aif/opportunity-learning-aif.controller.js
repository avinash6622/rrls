(function() {
	'use strict';

	angular.module('researchRepositoryLearningSystemApp').controller(
			'LearningAIFController', LearningAIFController);

	LearningAIFController.$inject = [ 'OpportunityLearningAIF',
			'OpportunityMaster', 'LearningAIF', 'ParseLinks', 'Principal',
			'AlertService', 'paginationConstants', '$scope', '$filter',
			'pagingParams', '$state', '$http', '$sce' ,'$uibModal'];

	function LearningAIFController(OpportunityLearningAIF,
			OpportunityMaster, LearningAIF, ParseLinks, Principal,
			AlertService, paginationConstants, $scope, $filter, pagingParams,
			$state, $http, $sce, $uibModal) {

		var vm = this;
		
		vm.learningAIF = [];
		vm.opportunityMasters = [];		
		vm.loadPage = loadPage;		
		vm.itemsPerPage = paginationConstants.itemsPerPage;	
		vm.predicate = pagingParams.predicate;		
		vm.reverse = pagingParams.ascending;		
		vm.edit = true;
		vm.page = 1;
		vm.pageFixed = 1;
		vm.links = {
			last : 0
		};
		
		vm.reset = reset;		
		vm.reverse = true;		
		vm.transition = transition;		
		vm.itemsValue = 'Learnings';
		vm.account = null;
		vm.subject = subject;
		vm.subjectLearnings = [];	
		vm.fixedCollapse = fixedCollapse;
		vm.fixedMap = fixedMap;
		vm.selectedItem=selectedItem;
		
		$scope.isCollapsed = [];
		vm.clear = clear;
		vm.loadAll = loadAll;		
		vm.name = '';
		vm.selectedList = [];
		
		vm.loadAll();
		

		function subject(subjectName) {			
			OpportunityLearningAIF.subjectLearning({
				subject : subjectName
			}, function(response) {
				console.log(response);
				vm.subjectLearnings = response;
			});
		}
		function fixedMap(selectedList) {
			console.log(vm.remove)
			selectedList.removeOpportunityMaster=vm.remove;
			console.log('afterList',selectedList);

			LearningAIF.update(selectedList, function(response) {
				console.log(response);

			});
			//vm.edit = false;
		}
		
		function selectedItem(val1) {
			console.log('logs', val1);

		}
		
		$scope.clickTaggedOpportunity = function(item) {
			console.log(item);
			$state.go('opportunity-master-detail', {id: item.id});
		};
		
		$scope.afterSelectItem = function(item) {
			console.log('Remove',item);
			
		}
		
		  $scope.addLearning = function () {

	            var modalInstance = $uibModal.open({
	                templateUrl: 'app/entities/opportunity-learning-aif/opportunity-learning-aif-dialog.html',
	                controllerAs: '$ctrl',
	                controller: 'LearningAIFDialogController',
	              size: 'lg',
	             /*  resolve: {
	                	options: function() {
	                		return vm.opportunityMaster;
	                	}
	                }*/
	            });

	        
	        };
		var myDate = new Date();

		$scope.currentYear = $filter('date')(myDate, 'yyyy');
		
		function fixedCollapse(index, state) {
			
			vm.learningAIF.forEach(function(value, key) {
				if (key === index) {
					value.expanded = state;
				} else {
					value.expanded = false;
				}
			});
			vm.edit = true;
		}

		vm.autoCompleteOpportunity = {
			minimumChars : 1,
			dropdownHeight : '200px',

			data : function(searchText) {
				return $http.get('api/opportunity-learnings/search').then(
						function(response) {
							searchText = searchText.toLowerCase();						
							var states = _.filter(response.data,
									function(state) {
									
										return (state.oppName).toLowerCase()
												.startsWith(searchText);

									});

							return states;
						});
			},
			renderItem : function(item) {
				return {
					value : item,
					label : $sce.trustAsHtml("<p class='auto-complete'>"
							+ item.oppName + "</p>")
				};
			},

			item : function(e) {

				console.log(e);
				vm.selectedName = e;
				vm.name = e.item.oppName;
				vm.opportunityName = e.item;
			
			}
		}		

		function clear() {			
			vm.learningAIF= [];			
			vm.name = '';
			loadAll();			

		}

		function loadAll() {
			LearningAIF.query({
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort()
			}, onSuccessFixed, onError);
		
			OpportunityMaster.tagOpportunity(onSuccess3, onError);

		}
		

		function onSuccess3(data2, headers) {

			vm.opportunityMasters = data2;

		}

		function onSuccessFixed(data1, headers) {
		
			vm.links = ParseLinks.parse(headers('link'));
			vm.totalItems = headers('X-Total-Count');
			for (var i = 0; i < data1.length; i++) {
				vm.learningAIF.push(data1[i]);
				$scope.isCollapsed.push(true);
			}
			vm.queryCount = vm.totalItems;
			vm.page = pagingParams.page;
		}
	
		function onError(error) {
			AlertService.error(error.data.message);
		}

		function sort() {
			var result = [ vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc') ];
			console.log(vm.predicate);
			if (vm.predicate !== 'id') {
				result.push('id');
			}
			console.log(result);
			return result;
		}
	
		function reset() {
			vm.page = 0;
			 vm.learningAIF = [];
			loadAll();
		}
		
		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}
	
		function transition() {
			console.log($state.$current);
			$state.transitionTo('learning-aif', {
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});


		}

		
	}
})();
