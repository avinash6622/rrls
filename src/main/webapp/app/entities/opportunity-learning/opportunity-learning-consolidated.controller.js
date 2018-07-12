(function() {
	'use strict';

	angular.module('researchRepositoryLearningSystemApp').controller(
			'LearningConsolidatedController', LearningConsolidatedController);

	LearningConsolidatedController.$inject = [ 'OpportunityLearning',
			'OpportunityMaster', 'FixedLearning', 'ParseLinks', 'Principal',
			'AlertService', 'paginationConstants', '$scope', '$filter',
			'pagingParams', '$state', '$http', '$sce' ];

	function LearningConsolidatedController(OpportunityLearning,
			OpportunityMaster, FixedLearning, ParseLinks, Principal,
			AlertService, paginationConstants, $scope, $filter, pagingParams,
			$state, $http, $sce) {

		var vm = this;

		vm.opportunityLearnings = [];
		vm.fixedLearnings = [];
		vm.opportunityMasters = [];
		vm.loadPage = loadPage;
		vm.loadFixedPage = loadFixedPage;
		vm.itemsPerPage = paginationConstants.itemsPerPage;
		;
		//vm.itemsFixedPerPage = paginationConstants.itemsFixedPerPage;
		vm.predicate = pagingParams.predicate;
		// vm.predicateFixed = pagingParams.predicateFixed;
		vm.reverse = pagingParams.ascending;
		// vm.reverseFixed = pagingParams.ascending;
		vm.edit = true;
		vm.page = 1;
		vm.pageFixed = 1;
		vm.links = {
			last : 0
		};
		/*vm.linksFixed = {
		        last: 0
		    };
		 */
		vm.reset = reset;
		// vm.resetFixed = resetFixed;
		vm.reverse = true;
		// vm.reverseFixed = true;
		vm.transition = transition;
		vm.transitionFixed = transitionFixed;
		vm.itemsValue = 'Learnings';
		vm.account = null;
		vm.subject = subject;
		vm.subjectLearnings = [];
		vm.expandCollapse = expandCollapse;
		vm.fixedCollapse = fixedCollapse;
		vm.fixedMap = fixedMap;
		/* vm.selectIndex=selectIndex;*/
		$scope.isCollapsed = [];
		vm.clear = clear;
		vm.loadAll = loadAll;
		vm.loadAll1 = loadAll1;
		vm.name = '';
		vm.selectedList = [];
		/* $scope.expanded=false;*/

		vm.loadAll();
		vm.loadAll1();

		function subject(subjectName) {
			/*$scope.isCollapsed[index] = false;*/
			console.log('subject', subjectName);
			OpportunityLearning.subjectLearning({
				subject : subjectName
			}, function(response) {
				console.log(response);
				vm.subjectLearnings = response;
			});
		}
		function fixedMap(selectedList) {
			console.log('log', selectedList);

			FixedLearning.update(selectedList, function(response) {
				console.log(response);

			});
			vm.edit = false;
		}
		var myDate = new Date();

		$scope.currentYear = $filter('date')(myDate, 'yyyy');

		function expandCollapse(index, state) {
			console.log(index, state);
			vm.opportunityLearnings.forEach(function(value, key) {
				if (key === index) {
					value.expanded = state;
				} else {
					value.expanded = false;
				}
			});

		}
		function fixedCollapse(index, state) {
			console.log(index, state);
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
							//  console.log(searchText);
							// console.log(response);

							// ideally filtering should be done on the server
							var states = _.filter(response.data,
									function(state) {
										// console.log(state);
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

			itemSelected : function(e) {

				console.log(e);
				vm.selectedName = e;
				vm.name = e.item.oppName;
				vm.opportunityName = e.item;
				console.log(vm.opportunityName.id, 'NAme');

				OpportunityLearning.searchopportunity(vm.opportunityName,
						onSuccess1, onError1);
			}
		}

		function onSuccess1(data, headers) {

			console.log(data);

			vm.opportunityLearnings = data;

		}

		function onError1() {

		}

		function clear() {
			vm.opportunityLearnings = [];
			vm.fixedLearnings = [];
			console.log("hi");
			vm.name = '';
			loadAll();
			loadAll1();

		}

		function loadAll() {
			OpportunityLearning.consolidatedLearning({
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort()
			}, onSuccess, onError);

			/* FixedLearning.fixedLearning({
			     page: pagingParams.pageFixed - 1,
			     size: vm.itemsPerPage,
			     sort: sort()
			 }, onSuccess2, onError);*/

			//FixedLearning.fixedLearning(onSuccess2,onError);  
			OpportunityMaster.queryOpportunity(onSuccess3, onError);

		}
		function loadAll1() {
			FixedLearning.fixedLearning({
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort()
			}, onSuccess2, onError);
		}

		function onSuccess3(data2, headers) {

			/*  console.log('FixedLearning',data2);*/

			vm.opportunityMasters = data2;
			console.log('OpportunityNames', vm.opportunityMasters);

		}

		function onSuccess2(data1, headers) {

			/*  console.log('FixedLearning',data1);

			  vm.fixedLearnings = data1;
			  console.log('FixedLearning',vm.fixedLearnings);
			 */
			vm.links = ParseLinks.parse(headers('link'));
			vm.totalItems1 = headers('X-Total-Count');
			for (var i = 0; i < data1.length; i++) {
				vm.fixedLearnings.push(data1[i]);
				$scope.isCollapsed.push(true);
			}
			vm.queryCount1 = vm.totalItems1;
			vm.pageFixed = pagingParams.page;
		}
		function onSuccess(data, headers) {

			vm.links = ParseLinks.parse(headers('link'));
			vm.totalItems = headers('X-Total-Count');
			for (var i = 0; i < data.length; i++) {
				vm.opportunityLearnings.push(data[i]);
				/* $scope.isCollapsed.push(true);*/
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
		/*function sortFixed() {
		    var result = [vm.predicateFixed + ',' + (vm.reverseFixed ? 'asc' : 'desc')];
		    console.log(vm.predicateFixed);
		    if (vm.predicateFixed !== 'id') {
		        result.push('id');
		    }
		    console.log(result);
		    return result;
		}*/

		function reset() {
			vm.page = 0;
			vm.opportunityLearnings = [];
			loadAll();
		}
		/*  function resetFixed () {
		      vm.pageFixed = 0;
		      vm.fixedLearnings = [];
		      loadAll1();
		  }

		 */
		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}
		function loadFixedPage(Page) {

			vm.page = page;
			vm.transition();
		}

		function transition() {
			$state.transitionTo('consolidated-learnings', {
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});

		}

		function transitionFixed() {
			console.log($state.$current);
			$state.transitionTo('fixed-learnings', {
				page : vm.pageFixed,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});

		}
		/*  function transitionFixed () {
		      $state.transitionTo($state.$current, {
		          page: vm.pageFixed,
		          sort: vm.predicateFixed + ',' + (vm.reverseFixed ? 'asc' : 'desc'),
		          search: vm.currentSearch
		      });
		     
		  }*/
	}
})();
