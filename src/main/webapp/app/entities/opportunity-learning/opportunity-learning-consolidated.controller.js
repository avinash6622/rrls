(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('LearningConsolidatedController', LearningConsolidatedController);

    LearningConsolidatedController.$inject = ['OpportunityLearning', 'ParseLinks','Principal', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams','$state'];

    function LearningConsolidatedController(OpportunityLearning, ParseLinks,Principal, AlertService, paginationConstants,$scope,$filter,pagingParams,$state) {

        var vm = this;

        vm.opportunityLearnings = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;     
        vm.reverse = pagingParams.ascending;
        vm.page = 1;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.transition = transition;
        vm.itemsValue = 'Learnings';
        vm.account = null;
        vm.subject=subject;
        vm.subjectLearnings=[];
       /* vm.selectIndex=selectIndex;*/
        $scope.isCollapsed = true;
       /* $scope.expanded=false;*/
      

        loadAll();
     /*   $scope.$on('authenticationSuccess', function() {
            getAccount();
        });*/
  	  
  	 /* getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
             console.log('Account',account)
            });

        }
*/
        function subject(subjectName) {
        	/*$scope.isCollapsed[index] = false;*/
          console.log('subject',subjectName);
          OpportunityLearning.subjectLearning({subject:subjectName},function(response){
        	  console.log(response);
        	  vm.subjectLearnings=response;
          });
        }


        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

      /*  function selectIndex(index){
        	console.log('index',index);
        	angular.forEach(vm.opportunityLearnings, function(value, key) {
        		console.log(key);
        		
        		if(key==index)
        			{
        			console.log(key,index);
        			 vm.opportunityLearning[index]=$scope.expanded=true;
        			}
        		else{
        			console.log('false',key,index);
        			 vm.opportunityLearning[index]=$scope.expanded=false;
        		}
        	
        		  console.log(key + ': ' + value);
        		});
      
        	
        }*/
        function loadAll () {
            OpportunityLearning.consolidatedLearning({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                console.log(vm.predicate);
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                console.log(result);
                return result;
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
        }

        function reset () {
            vm.page = 0;
            vm.opportunityLearnings = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
