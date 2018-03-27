(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('HomeController', HomeController);


    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'AlertService', 'OpportunityMaster','ParseLinks', 'paginationConstants', '$state','$http','$filter','pagingParams'];

    function HomeController ($scope, Principal, LoginService, AlertService, OpportunityMaster,ParseLinks,paginationConstants, $state,$http,$filter,pagingParams) {
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
        vm.reverse = pagingParams.ascending;
        vm.predicate = pagingParams.predicate;
        vm.transition = transition;

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

        loadAll();

        function loadAll () {
         /*   OpportunityMaster.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
*/

    //
            $http.get('api/opportunity-summary/getdata/')
                .then(function(response) {
                    console.log("RESPONSE",response);
                    var len = response.data;

                   // vm.dashboardvalues =  response.data;
                    for (var i = 0; i < len.length; i++) {
                        vm.dashboardvalues.push(len[i]);
                    }
                    vm.totalItems =  vm.dashboardvalues.length;
                    vm.queryCount = vm.totalItems;

                });
                /*,onSuccess1(vm.dashboardvalues),onError1());

            function onSuccess1(data)
            {
                console.log("hiii",vm.oppmas);
               /!* vm.links = headers('link') ? ParseLinks.parse(headers('link')) : vm.links;*!/
                vm.totalItems = data.length;
                console.log(vm.totalItems);
                vm.queryCount = vm.totalItems;
                vm.page = pagingParams.page;

            }*/




        // console.log("dshkjhkfjs--->"+vm.dashboardvalues.opportunityMasterid.oppStatus);



          //  console.log("DASHBOARD VALUE",vm.dashboardvalues);



        }

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

    /*    function onSuccess(data, headers) {
           // vm.links = ParseLinks.parse(headers('link'));
            vm.links = headers('link') ? ParseLinks.parse(headers('link')) : vm.links;
            vm.totalItems = headers('X-Total-Count');
            console.log(vm.totalItems);
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.oppmas=data;
            console.log("jsdhjksnd--->", vm.oppmas)
          /!*  for (var i = 0; i < data.length; i++) {
                vm.opportunityMasters.push(data[i]);
            }*!/


        }
*/
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


        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();


