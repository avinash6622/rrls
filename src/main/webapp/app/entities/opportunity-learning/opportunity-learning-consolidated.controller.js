(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('LearningConsolidatedController', LearningConsolidatedController);

    LearningConsolidatedController.$inject = ['OpportunityLearning', 'ParseLinks','Principal', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams','$state','$http','$sce'];

    function LearningConsolidatedController(OpportunityLearning, ParseLinks,Principal, AlertService, paginationConstants,$scope,$filter,pagingParams,$state,$http,$sce) {

        var vm = this;

        vm.opportunityLearnings = [];
        vm.fixedLearnings=[];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.page = 1;
        vm.links = {
            last: 0
        };

        vm.reset = reset;
        vm.reverse = true;
        vm.transition = transition;
        vm.itemsValue = 'Learnings';
        vm.account = null;
        vm.subject=subject;
        vm.subjectLearnings=[];
        vm.expandCollapse = expandCollapse;
        vm.fixedCollapse = fixedCollapse;
       /* vm.selectIndex=selectIndex;*/
        $scope.isCollapsed = true;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.name = '';
       /* $scope.expanded=false;*/


        vm.loadAll();
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
                vm.opportunityName=e.item;
                console.log(vm.opportunityName.id,'NAme');


                OpportunityLearning.searchopportunity(vm.opportunityName,onSuccess1,onError1);
            }
        }


        function onSuccess1(data,headers){

            console.log(data);

            vm.opportunityLearnings = data;

        }


        function  onError1(){

        }


        function clear() {
            vm.opportunityLearnings = [];
            console.log("hi");
            vm.name = '';
            loadAll();

        }

        function loadAll () {
            OpportunityLearning.consolidatedLearning({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

            OpportunityLearning.fixedLearning(onSuccess2,onError);

        }
        function onSuccess2(data1,headers){

            console.log('FixedLearning',data1);

            vm.fixedLearnings = data1;
            console.log('FixedLearning',vm.fixedLearnings);

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
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            console.log(vm.predicate);
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            console.log(result);
            return result;
        }

        function reset () {
            vm.page = 0;
            vm.opportunityLearnings = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
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
