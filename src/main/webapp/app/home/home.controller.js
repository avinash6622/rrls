(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('HomeController', HomeController);


    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'AlertService', 'OpportunityMaster','ParseLinks','paginationConstants','$state','$http','$filter','pagingParams'];

    function HomeController ($scope, Principal, LoginService, AlertService, OpportunityMaster,ParseLinks,paginationConstants,$state,$http,$filter,pagingParams) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.itemsValue = 'Opportunities';
        vm.opportunityMasters = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 1;
        vm.oppmas=[];
        vm.totalItems = null;
        vm.links = {
            last: 0
        };
     //   vm.predicate = 'id';
       // vm.reset = reset;
        vm.reverse = true;
        vm.reverse = pagingParams.ascending;
        vm.predicate = pagingParams.predicate;
       /* vm.transition = transition;*/
        vm.predicate = 'id';

        vm.dashboardvalues = [];

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });


        var myDate = new Date();



        var previousYear = new Date(myDate);

        previousYear.setYear(myDate.getFullYear()-1);

        var previousYearBefore = new Date(previousYear);
        previousYearBefore.setYear(previousYear.getFullYear()-1);



        var nextYear = new Date(myDate);

        nextYear.setYear(myDate.getFullYear()+1);


        var nextYearNext = new Date(nextYear);

        nextYearNext.setYear(nextYear.getFullYear()+1);

        $scope.marginTopSize = '10px;';

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        $scope.nextYear = $filter('date')(nextYear,'yyyy');

        $scope.prevYear = $filter('date')(previousYear,'yyyy');

        $scope.prevYearBefore = $filter('date')(previousYearBefore,'yyyy');

        $scope.neYearNext = $filter('date')(nextYearNext,'yyyy');



        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

      /*  loadAll();*/

      /*  function loadAll () {*/
        	console.log($state.params);






            if ($state.params && $state.params.createdBy) {

	           $http.get('api/opportunity-summary/getdata/' + $state.params.createdBy)
	            .then(function(response) {
	            	vm.dashboardvalues = [];
	                console.log("RESPONSE");
	                var len = response.data;

	               // vm.dashboardvalues =  response.data;
	                for (var i = 0; i < len.length; i++) {
	                    vm.dashboardvalues.push(len[i]);
	                }
	                vm.totalItems =  vm.dashboardvalues.length;
	                vm.queryCount = vm.totalItems;

	            });
                $scope.order = function (predicate) {
                    vm.reverse = (vm.predicate === vm.predicate) ? !vm.reverse : false;
                    vm.predicate = predicate;
                };

                $scope.paginate = function (value) {
                    var begin, end, index;
                    begin = (vm.page - 1) * vm.itemsPerPage;
                    end = begin + vm.itemsPerPage;
                    index = vm.dashboardvalues.indexOf(value);
                    return (begin <= index && index < end);
                };

            }
else{
                $http.get('api/opportunity-summary/getdata')
                    .then(function(response) {
                        console.log("RESPONSEs");
                        var len = response.data;

                        // vm.dashboardvalues =  response.data;
                        for (var i = 0; i < len.length; i++) {
                            vm.dashboardvalues.push(len[i]);
                        }
                        vm.totalItems =  vm.dashboardvalues.length;
                        vm.queryCount = vm.totalItems;

                    });
                $scope.order = function (predicate) {
                    vm.reverse = (vm.predicate === vm.predicate) ? !vm.reverse : false;
                    vm.predicate = predicate;
                };

                $scope.paginate = function (value) {
                    var begin, end, index;
                    begin = (vm.page - 1) * vm.itemsPerPage;
                    end = begin + vm.itemsPerPage;
                    index = vm.dashboardvalues.indexOf(value);
                    return (begin <= index && index < end);
                };

            }
            }

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }


        function onError(error) {
            AlertService.error(error.data.message);
        }
     /*   function reset () {
            vm.page = 0;
            vm.opportunityMasters = [];
            loadAll();
        }*/
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }


    /*    function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }*/
/*    }*/
})();


