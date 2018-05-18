(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('HomeController', HomeController);


    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'AlertService', 'OpportunityMaster','ParseLinks','paginationConstants','$http','$filter','pagingParams','DecimalConfiguration','$state','$timeout'];

    function HomeController ($scope, Principal, LoginService, AlertService, OpportunityMaster,ParseLinks,paginationConstants,$http,$filter,pagingParams,DecimalConfiguration,$state,$timeout) {
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
       vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.reverse = pagingParams.ascending;
        vm.predicate = pagingParams.predicate;
      vm.transition = transition;
      vm.loader = false;


      //  vm.predicate = 'id';

        vm.dashboardvalues = [];

        vm.multiplevalue = null;

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

       // vm.loadPage(vm.page);

        getAccount();

        loadAll()

        $timeout(function() {
            getDecimalConfig();
        }, 3000);



        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }


        function getDecimalConfig() {


            DecimalConfiguration.get({id:vm.account.id},onSuccess1,onError1);





        }


       function onSuccess1(resp){

           console.log(resp);

           console.log(resp.rupee);

           console.log(vm.account.login);







           if(resp.rupee == 'Millions')
           {
               if(vm.account.login == resp.user.login)
               {
                   if(!$state.params.createdBy)
                   {
                       vm.multiplevalue = 10;
                   }

               }


           }

           $timeout(function() {
               $("#mytable").CongelarFilaColumna({
                   Columnas: 5,
                   width: '100%',
                   height: '100%'
               });
               $("#table-scroll").css({
                   'visibility': 'visible'
               });

               vm.loader = false;

           }, 2000);




       }

       function onError1(){

       }




        	//console.log($state.params);

        function loadAll(){
                   console.log($state.params);


            if ($state.params && $state.params.createdBy){
                OpportunityMaster.opportunitysummaryDataCreatedBy({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    createdBy:$state.params.createdBy
                },onSuccess, onError)
            }

            else{

                OpportunityMaster.opportunitysummaryData({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,


                }, onSuccess, onError);

            }




        }

        function onSuccess(data, headers) {

            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.dashboardvalues = data;
            vm.loader = true;

        }
        function onError(error) {
            AlertService.error(error.data.message);
        }


/*
        function onSuccess1(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.dashboardvalues = data;

        }
        function onError1(error) {
            AlertService.error(error.data.message);
        }*/








 /*           if ($state.params && $state.params.createdBy) {

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
                    console.log(value);
                    var begin, end, index;
                    begin = (vm.page - 1) * vm.itemsPerPage;
                    end = begin + vm.itemsPerPage;
                    index = vm.dashboardvalues.indexOf(value);
                    return (begin <= index && index < end);
                };

            }*/


        /*    function onsuccess(data,headers)
            {
                vm.links = ParseLinks.parse(headers('link'));
                vm.queryCount = headers('X-Total-Count');
            }*/








        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
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


        /*    setTimeout(function() {
                $("#mytable").CongelarFilaColumna({
                    Columnas: 5,
                    width: '100%',
                    height: '100%'
                });
            }, 2000);*/


        }



        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch,
                createdBy:$state.params.createdBy
            });


        }



        }





/*    }*/
})();


