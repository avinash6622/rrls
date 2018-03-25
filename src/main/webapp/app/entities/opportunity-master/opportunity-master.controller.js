(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterController', OpportunityMasterController);

    OpportunityMasterController.$inject = ['OpportunityMaster', 'ParseLinks', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams','$state'];

    function OpportunityMasterController(OpportunityMaster, ParseLinks, AlertService, paginationConstants,$scope,$filter,pagingParams,$state) {

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

        loadAll();



        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        function loadAll () {
            OpportunityMaster.query({
                page: pagingParams.page - 1,
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
                vm.queryCount = vm.totalItems;
                vm.page = pagingParams.page;

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

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
