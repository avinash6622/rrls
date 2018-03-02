(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('HomeController', HomeController);


    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'OpportunityMaster','ParseLinks', 'paginationConstants', '$state'];

    function HomeController ($scope, Principal, LoginService, OpportunityMaster,ParseLinks,paginationConstants, $state) {
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
        function register () {
            $state.go('register');
        }

        loadAll();

        function loadAll () {
            OpportunityMaster.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);



        OpportunityMaster.getsummarydata(function (resp){
            console.log(resp);

         },function (err) {
            console.log(err);
        });

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
