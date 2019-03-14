(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityNameListController', OpportunityNameListController);

    OpportunityNameListController.$inject = ['OpportunityName', 'ParseLinks', 'Principal', 'AlertService', 'paginationConstants', '$scope', '$filter', 'pagingParams', '$state', '$http', '$sce', '$q'];

    function OpportunityNameListController(OpportunityName, ParseLinks, Principal, AlertService, paginationConstants, $scope, $filter, pagingParams, $state, $http, $sce) {
        let vm = this;
        vm.opportunityNames = [];
        vm.loadPage = loadPage;
        vm.loadAll = loadAll;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.totalItems = null;
        vm.page = 1;
        vm.links = null;
        vm.transition = transition;

        vm.itemsValue = 'Opportunities';
        console.log('vm.itemsPerPage -' + vm.itemsPerPage);

        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log(Principal.isAuthenticated);
            });

        }



        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        vm.loadAll();

        function loadAll() {
            console.log('load all invoked');

            OpportunityName.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
        }

        function onSuccess(data, headers) {
            console.log(headers);
            console.log(headers('link'));
            console.log(headers('X-Total-Count'));
            console.log(pagingParams.page);
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.opportunityNames = data;
            console.log(vm.opportunityNames);
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
    }
})();

