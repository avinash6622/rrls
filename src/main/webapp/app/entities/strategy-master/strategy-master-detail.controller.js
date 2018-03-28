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
        vm.page = 1;
        vm.opportunitySummaryData = vm.strategyMaster.opportunitySummaryData;
        console.log(vm.opportunitySummaryData.length);
        vm.itemsValue = 'Opportunities';

        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:strategyMasterUpdate', function(event, result) {
            vm.strategyMaster = result;

            });
        $scope.$on('$destroy', unsubscribe);

        console.log(vm.strategyMaster);

        vm.totalItems = vm.opportunitySummaryData.length;
        vm.queryCount = vm.totalItems;
        vm.page = pagingParams.page;
        vm.transition = transition;

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
                search: vm.currentSearch
            });
        }


    }
})();
