(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterController', OpportunityMasterController)
        .filter('startFrom', function () {
        return function (input, start) {
            if (input) {
                start = +start;
                return input.slice(start);
            }
            return [];
        };
    });

    OpportunityMasterController.$inject = ['OpportunityMaster', 'ParseLinks','Principal', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams','$state','$http','$sce'];

    function OpportunityMasterController(OpportunityMaster, ParseLinks,Principal, AlertService, paginationConstants,$scope,$filter,pagingParams,$state,$http,$sce) {

        var vm = this;

        vm.opportunityMasters = [];
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
        vm.itemsValue = 'Opportunities';
        vm.account = null;
        vm.opportunityMaster='';
        vm.name='';
        vm.filterName=filterName;
        
        loadAll();
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

  	  getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
             console.log('Account',account)
            });

        }
function filterName(id){
/*	console.log('Full',id);*/
}
        vm.autoCompleteOpportunity = {
                minimumChars : 1,
                dropdownHeight : '200px',
                data : function(searchText) {
                    return $http.get('api/opportunity-masters').then(
                        function(response) {
                            searchText = searchText.toLowerCase();



                            // ideally filtering should be done on the server
                            var states = _.filter(response.data,
                                function(state) {
                                    return (state.masterName.oppName).toLowerCase()
                                        .startsWith(searchText);

                                });

                           /*  return _.pluck(states, 'sectorType');*/
                            return states;
                        });
                },
                renderItem : function(item) {
                    return {
                        value : item,
                        label : $sce.trustAsHtml("<p class='auto-complete'>"
                            + item.masterName.oppName + "</p>")
                    };
                },

                itemSelected : function(e) {


                	vm.selectedName = e;
                    vm.name = e.item.masterName.oppName;
                    vm.opportunityMaster=e.item;
                    console.log(vm.opportunityMaster,'NAme');
                    // state.airport = e.item;
                }
            }

        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        function loadAll () {
            OpportunityMaster.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);


        }


        function onSuccess(data, headers) {

            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            for (var i = 0; i < data.length; i++) {
                vm.opportunityMasters.push(data[i]);
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
            vm.opportunityMasters = [];
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
