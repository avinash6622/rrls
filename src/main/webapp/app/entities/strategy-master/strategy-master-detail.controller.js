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

    StrategyMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'StrategyMaster','$filter','pagingParams','ParseLinks','paginationConstants','$state','$timeout'];

    function StrategyMasterDetailController($scope, $rootScope, $stateParams, previousState, StrategyMaster,$filter,pagingParams,ParseLinks,paginationConstants,$state,$timeout) {
        var vm = this;
        vm.sortTable = sortTable;
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
        vm.loader = false;


        loadAll()


        setTimeout(function() {
            getDecimalConfig();

        }, 3000);


        function getDecimalConfig(){



        }

function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            console.log(vm.predicate);
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            console.log('strategy-master --->', result);
            return result;
        }


        function loadAll(){
            console.log('loadALL Data -->', $state.params);



              StrategyMaster.getStrategyDetail({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    id: $stateParams.id,
                    sort: sort()
                },onSuccess, onError)


        }


        function onSuccess(data, headers) {

            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.strategyvalues = data;

            console.log(vm.strategyvalues[0]);

            vm.loader = true;

          $timeout(function() {

              console.log(vm.loader);
                $("#mytable1").CongelarFilaColumna({
                    Columnas: 5,
                    width: '100%',
                    height: '100%'
                });

              $("#table-table").css({
                  'visibility': 'visible'
              });

              vm.loader = false;
            }, 2000);


        }
        function onError(error) {
            AlertService.error(error.data.message);
        }


        function loadPage(page) {
            vm.page = page;
            loadAll();





        }

  function sortTable(n) {
      var table, rows, switching, i, x, y, shouldSwitch;
      table = document.getElementById("mytable1");
      switching = true;
      // Set the sorting direction to ascending:
      var dir = "asc";
      /* Make a loop that will continue until
      no switching has been done: */
      while (switching) {
          // Start by saying: no switching is done:
          switching = false;
          rows = table.rows;
          /* Loop through all table rows (except the
          first, which contains table headers): */
          for (i = 1; i < (rows.length - 1); i++) {
              // Start by assuming no switching is needed:
              shouldSwitch = false;
              /* Get the two elements you want to compare,
              one from the current row and one from the next: */
              x = rows[i].getElementsByTagName("TD")[n];
              y = rows[i + 1].getElementsByTagName("TD")[n];
              /* Check if the two rows should switch place,
              based on the direction, which is always ascending: */
              if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                  // If so, mark as a switch and break the loop:
                  shouldSwitch = true;
                  break;
              }
          }
          if (shouldSwitch) {
              /* If a switch has been marked, make the switch
              and mark that a switch has been done: */
              rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
              switching = true;
          }
      }
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

