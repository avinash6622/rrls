(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('HomeController', HomeController);


    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'AlertService', 'OpportunityMaster','ParseLinks', 'paginationConstants', '$state','$http','$filter'];

    function HomeController ($scope, Principal, LoginService, AlertService, OpportunityMaster,ParseLinks,paginationConstants, $state,$http,$filter) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        vm.opportunityMasters = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

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

        loadAll();

        function loadAll () {
            /*OpportunityMaster.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);*/


    // console.log("DATA------>"+vm.opportunityMasters);
            $http.get('api/opportunity-summary/getdata/')
                .then(function(response) {
                    console.log("RESPONSE",response);
                    var len = response.data;

                   // vm.dashboardvalues =  response.data;
                    for (var i = 0; i < len.length; i++) {
                        vm.dashboardvalues.push(len[i]);
                    }
                });




        // console.log("dshkjhkfjs--->"+vm.dashboardvalues.opportunityMasterid.oppStatus);
            $scope.checkstatus = function(arr2)
            {
                if(arr2.opportunityMasterid.oppStatus === 'Approved')
                {
                    return true;
                }
                else
                {
                    return false
                }
            }


            console.log("DASHBOARD VALUE",vm.dashboardvalues);

       /*     OpportunityMaster.getsummarydata({}, function (data, headers){
                console.log(data);
                console.log(headers);

                for (var i = 0; i < data.length; i++) {
                    vm.dashboardvalues.push(data[i]);
                }
                console.log(  vm.dashboardvalues)
            },function (err) {
                console.log(err);
            });*/


        }

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function onSuccess(data, headers) {
            vm.links = headers('link') ? ParseLinks.parse(headers('link')) : vm.links;
            vm.totalItems = headers('X-Total-Count');
            for (var i = 0; i < data.length; i++) {
                vm.opportunityMasters.push(data[i]);
            }


        }

        function onError(error) {
            AlertService.error(error.data.message);
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
    }
})();


/*(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterController', OpportunityMasterController);


    OpportunityMasterController.$inject = ['$scope','OpportunityMaster', 'Principal', 'LoginService', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function OpportunityMasterController($scope,OpportunityMaster, Principal, LoginService, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.opportunityMasters = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        loadAll();

        function loadAll () {
            OpportunityMaster.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.opportunityMasters.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
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
    }
})();
*/
