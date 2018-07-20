(function() {
	'use strict';

	angular.module('researchRepositoryLearningSystemApp').controller(
			'LearningConsolidatedController', LearningConsolidatedController);

	LearningConsolidatedController.$inject = [ 'OpportunityLearning',
			'OpportunityMaster', 'FixedLearning', 'ParseLinks', 'Principal',
			'AlertService', 'paginationConstants', '$scope', '$filter',
			'pagingParams', '$state', '$http', '$sce' ,'$uibModal'];

	function LearningConsolidatedController(OpportunityLearning,
			OpportunityMaster, FixedLearning, ParseLinks, Principal,
			AlertService, paginationConstants, $scope, $filter, pagingParams,
			$state, $http, $sce, $uibModal) {

		var vm = this;
		
		vm.fixedLearnings = [];
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
			OpportunityLearning.subjectLearning({
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

			FixedLearning.update(selectedList, function(response) {
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
	                templateUrl: 'app/entities/fixed-learning/fixed-learning-dialog.html',
	                controllerAs: '$ctrl',
	                controller: 'FixedLearningController',
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
			
			vm.fixedLearnings.forEach(function(value, key) {
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
			vm.fixedLearnings = [];			
			vm.name = '';
			loadAll();			

		}

		function loadAll() {
			FixedLearning.fixedLearning({
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
				vm.fixedLearnings.push(data1[i]);
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
			 vm.fixedLearnings = [];
			loadAll();
		}
		
		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}
	
		function transition() {
			console.log($state.$current);
			$state.transitionTo('fixed-learnings', {
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});


		}

		
	}
})();
