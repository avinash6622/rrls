(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterController', OpportunityMasterController)


    OpportunityMasterController.$inject = ['OpportunityMaster', 'ParseLinks', 'Principal', 'AlertService', 'paginationConstants', '$scope', '$filter', 'pagingParams', '$state', '$http', '$sce', '$q'];

    function OpportunityMasterController(OpportunityMaster, ParseLinks, Principal, AlertService, paginationConstants, $scope, $filter, pagingParams, $state, $http, $sce) {

        var vm = this;

        vm.opportunityMasters = [];
        vm.loadPage = loadPage;
        vm.loadAll = loadAll;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.totalItems = null;
        vm.page = 1;
        vm.links = null;
        //  vm.reset = reset;
        //  vm.reverse = true;
        vm.transition = transition;
        vm.itemsValue = 'Opportunities';
        vm.account = null;
        vm.opportunityName = '';
        vm.name = '';
        vm.filterName = filterName;
        vm.clear = clear;

        vm.loadAll();
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

        function filterName(id) {
            /*	console.log('Full',id);*/
        }


        vm.autoCompleteOpportunity = {
            minimumChars: 1,
            dropdownHeight: '200px',
            data: function (searchText) {
                return $http.get('api/opportunity-masters/all').then(
                    function (response) {
                        searchText = searchText.toLowerCase();
                        var states = _.filter(response.data,
                            function (state) {
                                // console.log(state);
                                return (state.oppName).toLowerCase()
                                    .startsWith(searchText);
                            });

                        return states;
                    });
            },
            renderItem: function (item) {
                return {
                    value: item,
                    label: $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.oppName + "</p>")
                };
            },
            itemSelected: function (e) {
                console.log(e);
                vm.selectedName = e;
                vm.name = e.item.oppName;
                vm.opportunityName = e.item;
                OpportunityMaster.getSearchOpportunity(vm.opportunityName, onSuccess1, onError1);
            }
        }

        function onSuccess1(data, headers) {

            console.log(data);
            console.log(vm.name);
            vm.opportunityMasters = data;

        }

        function onError1() {


        }

        function clear() {
            vm.name = '';
            loadAll();
        }

        var myDate = new Date();

        $scope.currentYear = $filter('date')(myDate, 'yyyy');

        function loadAll() {
            console.log('opp master all');

            OpportunityMaster.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);


        }

        function onSuccess(data, headers) {
            // console.log(headers);
            console.log('link - '+headers('link'));
            console.log(headers('X-Total-Count'));
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            /*   for (var i = 0; i < data.length; i++) {
                   vm.opportunityMasters.push(data[i]);
               }*/

            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.opportunityMasters = data;
            // console.log('vm.opportunityMasters');
            // console.log(vm.opportunityMasters);


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

        function reset() {
            vm.page = 0;
            vm.opportunityMasters = [];
            loadAll();
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
    }
})();


/*
(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterController', OpportunityMasterController)


    OpportunityMasterController.$inject = ['OpportunityMaster', 'ParseLinks','Principal', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams','$state','$http','$sce'];

    function OpportunityMasterController(OpportunityMaster, ParseLinks,Principal, AlertService, paginationConstants,$scope,$filter,pagingParams,$state,$http,$sce) {

        var vm = this;

        vm.opportunityMasters = [];
      //  vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.page = 1;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
      //  vm.reset = reset;
        vm.reverse = true;
       // vm.transition = transition;
        vm.itemsValue = 'Opportunities';
        vm.account = null;
        vm.opportunityMaster='';
        vm.name='';
        vm.filterName=filterName;

        //  loadAll();
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log('Account',account)
            });

        }
        function filterName(id){
            /!*	console.log('Full',id);*!/
        }
        vm.autoCompleteOpportunity = {
            minimumChars : 1,
            dropdownHeight : '200px',
            data : function(searchText) {
                return $http.get('api/opportunity-masters').then(
                    function(response) {
                        searchText = searchText.toLowerCase();



                        // ideally filtering should be done on the server
                        var states = _.filter(response.data,
                            function(state) {
                                return (state.masterName.oppName).toLowerCase()
                                    .startsWith(searchText);

                            });

                        /!*  return _.pluck(states, 'sectorType');*!/
                        return states;
                    });
            },
            renderItem : function(item) {
                return {
                    value : item,
                    label : $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.masterName.oppName + "</p>")
                };
            },

            itemSelected : function(e) {


                vm.selectedName = e;
                vm.name = e.item.masterName.oppName;
                vm.opportunityMaster=e.item;
                console.log(vm.opportunityMaster,'NAme');
                // state.airport = e.item;
            }
        }

        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        //  function loadAll () {
        OpportunityMaster.query({

        }, onSuccess, onError);


        //   }


        function onSuccess(data, headers) {


            for (var i = 0; i < data.length; i++) {
                vm.opportunityMasters.push(data[i]);
            }
            vm.totalItems = headers('X-Total-Count');;
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;


            $scope.order = function (predicate) {
                vm.reverse = (vm.predicate === vm.predicate) ? !vm.reverse : false;
                vm.predicate = predicate;
            };

            $scope.paginate = function (value) {
                var begin, end, index;
                begin = (vm.page - 1) * vm.itemsPerPage;
                end = begin + vm.itemsPerPage;
                index = vm.opportunityMasters.indexOf(value);
                return (begin <= index && index < end);
            };


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

        function reset () {
            vm.page = 0;
            vm.opportunityMasters = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

 /!*       function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }*!/
    }
})();
*/
