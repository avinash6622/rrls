/*
(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('StrategyMasterDetailController', StrategyMasterDetailController);

    StrategyMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StrategyMaster','$filter','pagingParams','ParseLinks','paginationConstants','$state'];

    function StrategyMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, StrategyMaster,$filter,pagingParams,ParseLinks,paginationConstants,$state) {
        var vm = this;

        vm.strategyMaster = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = true;
        vm.page = 1;
        vm.opportunitySummaryData = vm.strategyMaster.opportunitySummaryData;

        vm.itemsValue = 'Opportunities';



        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:strategyMasterUpdate', function(event, result) {
            vm.strategyMaster = result;

            });
        $scope.$on('$destroy', unsubscribe);

        vm.predicate = 'id';
        vm.totalItems = vm.opportunitySummaryData.length;
        vm.queryCount = vm.totalItems;
        vm.page = pagingParams.page;
      /!*  vm.transition = transition;*!/

        $scope.order = function (predicate) {
           vm.reverse = (vm.predicate === vm.predicate) ? !vm.reverse : false;
            vm.predicate = predicate;
          };

          $scope.paginate = function (value) {
            var begin, end, index;
            begin = (vm.page - 1) * vm.itemsPerPage;
            end = begin + vm.itemsPerPage;
            index = vm.opportunitySummaryData.indexOf(value);
            return (begin <= index && index < end);
          };

/!*        setTimeout(function() {
            $("#mytable1").CongelarFilaColumna({
                Columnas: 5,
                width: '100%',
                height: '100%'
            });
        }, 2000);*!/

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

  /!*      function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }*!/


    }
})();
*/



(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('StrategyMasterDetailController', StrategyMasterDetailController);

    StrategyMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'StrategyMaster','$filter','pagingParams','ParseLinks','paginationConstants','$state'];

    function StrategyMasterDetailController($scope, $rootScope, $stateParams, previousState, StrategyMaster,$filter,pagingParams,ParseLinks,paginationConstants,$state) {
        var vm = this;

       // vm.strategyMaster = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = true;
        vm.page = 1;
      //  vm.opportunitySummaryData = vm.strategyMaster.opportunitySummaryData;

        vm.itemsValue = 'Opportunities';



   /*     var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:strategyMasterUpdate', function(event, result) {
            vm.strategyMaster = result;

        });
        $scope.$on('$destroy', unsubscribe);*/



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
        vm.strategyvalues = [];


        loadAll()


        setTimeout(function() {
            getDecimalConfig();

        }, 3000);


        function getDecimalConfig(){



        }



        function loadAll(){
            console.log($state.params);



              StrategyMaster.getStrategyDetail({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    id: $stateParams.id
                },onSuccess, onError)


        }


        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.strategyvalues = data;

            console.log(vm.strategyvalues[0]);

            setTimeout(function() {
                $("#mytable1").CongelarFilaColumna({
                    Columnas: 5,
                    width: '100%',
                    height: '100%'
                });
                $("#table-table").css({
                    'visibility': 'visible'
                });
            }, 2000);


        }
        function onError(error) {
            AlertService.error(error.data.message);
        }


        function loadPage(page) {
            vm.page = page;
            loadAll();





        }


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

              function transition () {
                  $state.transitionTo($state.$current, {
                      page: vm.page,
                      sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                      search: vm.currentSearch,
                      id: $stateParams.id
                  });
              }


    }
})();

