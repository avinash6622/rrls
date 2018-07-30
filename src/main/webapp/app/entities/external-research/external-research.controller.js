(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ExternalResearchAnalystController', ExternalResearchAnalystController);

    ExternalResearchAnalystController.$inject = ['ExternalResearchAnalyst', 'ParseLinks', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams'];

    function ExternalResearchAnalystController(ExternalResearchAnalyst, ParseLinks, AlertService, paginationConstants,$scope,$filter,pagingParams) {

        var vm = this;

        vm.externalResearch = [];
        vm.loadPage = loadPage;
        vm.loadAll = loadAll;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.totalItems = null;
        vm.page = 1;
        vm.links = null;

       vm.reset = reset;
      //  vm.reverse = true;
        vm.transition = transition;
        vm.itemsValue = 'external RA';

        vm.loadAll();


        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        function loadAll () {
        	  ExternalResearchAnalyst.query({
                  page: pagingParams.page - 1,
                  size: vm.itemsPerPage,
                  sort: sort()
              }, onSuccess, onError);
        }
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
              
                  vm.queryCount = vm.totalItems;
                  vm.page = pagingParams.page;
                  vm.externalResearch = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }


        }      


        function reset () {
            vm.page = 0;
            vm.externalResearch = [];
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
)();

