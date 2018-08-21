(function() {
	'use strict';

	angular.module('researchRepositoryLearningSystemApp').controller(
			'CommunicationLetterController', CommunicationLetterController);

	CommunicationLetterController.$inject = [ 'CommunicationLetters',
			'OpportunityMaster', 'ParseLinks', 'Principal',
			'AlertService', 'paginationConstants', '$scope', '$filter',
			'pagingParams', '$state', '$http', '$sce'];

	function CommunicationLetterController(CommunicationLetters,
			OpportunityMaster, ParseLinks, Principal,
			AlertService, paginationConstants, $scope, $filter, pagingParams,
			$state, $http, $sce) {

		var vm = this;
		
		vm.communicationLetters = [];		
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
		vm.itemsValue = 'Communication Letters';
		vm.account = null;
		
		vm.clear = clear;
		vm.loadAll = loadAll;		
		vm.name = '';
		
		vm.loadAll();
		

		var myDate = new Date();

		$scope.currentYear = $filter('date')(myDate, 'yyyy');
		
		
			function clear() {			
				vm.communicationLetters=[];			
			vm.name = '';
			loadAll();			

		}

		function loadAll() {
			CommunicationLetters.query({
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort()
			}, onSuccessFixed, onError);
		
			
			  $scope.$on('authenticationSuccess', function() {
                  getAccount();});       

		}
		
		  getAccount();

	      function getAccount() {
	          Principal.identity().then(function(account) {
	              vm.account = account;
	              vm.isAuthenticated = Principal.isAuthenticated;
	          
	          });

	      }
		

	
		function onSuccessFixed(data1, headers) {
		
			vm.links = ParseLinks.parse(headers('link'));
			vm.totalItems = headers('X-Total-Count');
			for (var i = 0; i < data1.length; i++) {
				vm.communicationLetters.push(data1[i]);
				
			}
			console.log('Letters',vm.communicationLetters);
			vm.queryCount = vm.totalItems;
			vm.page = pagingParams.page;
		}
	
		function onError(error) {
			AlertService.error(error.data.message);
		}

		function sort() {
			var result = [ vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc') ];
			console.log(vm.reverse);
			if (vm.predicate !== 'id') {
				result.push('id');
			}
			console.log(result);
			return result;
		}
	
		function reset() {
			vm.page = 0;
			 vm.communicationLetters = [];
			loadAll();
		}
		
		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}
	
		function transition() {
			console.log($state.$current);
			$state.transitionTo($state.$current, {
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});


		}

		
	}
})();
