(function() {
    'use strict';

    angular.module('researchRepositoryLearningSystemApp').controller(
        'ConfidentialLetterController', ConfidentialLetterController);

    ConfidentialLetterController.$inject = [ 'ConfidentialLetters',
        'OpportunityMaster', 'ParseLinks', 'Principal',
        'AlertService', 'paginationConstants', '$scope', '$filter',
        'pagingParams', '$state', '$http', '$sce'];

    function ConfidentialLetterController(ConfidentialLetters,
                                           OpportunityMaster, ParseLinks, Principal,
                                           AlertService, paginationConstants, $scope, $filter, pagingParams,
                                           $state, $http, $sce) {

        var vm = this;

        vm.communicationLetters = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.edit = true;
        vm.page = 1;
        vm.pageFixed = 1;
        vm.links = {
            last : 0
        };
        vm.transition = transition;
        vm.itemsValue = 'Communication Letters';
        vm.account = null;
        vm.clearOpp = clearOpp;
        vm.clearSub = clearSub;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.name = '';
        vm.subject='';

        vm.loadAll();


        var myDate = new Date();
        $scope.currentYear = $filter('date')(myDate, 'yyyy');
        function clear() {
            vm.communicationLetters=[];
            vm.name = '';
            loadAll();
        }
        vm.autoCompleteOpportunity1 = {
            minimumChars : 1,
            dropdownHeight : '200px',

            data : function(searchText) {
                return $http.get('api/communication-letter/opportunity').then(
                    function(response) {
                        searchText = searchText.toLowerCase();
                        var states = _.filter(response.data,
                            function(state) {

                                return (state.oppName).toLowerCase()
                                    .startsWith(searchText);

                            });

                        return states;
                    });
            },
            renderItem : function(item) {
                return {
                    value : item,
                    label : $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.oppName + "</p>")
                };
            },

            itemSelected : function(e) {
                console.log('itemSelected call invoked');
                console.log(e);
                vm.selectedName = e;
                vm.name = e.item.oppName;
                vm.opportunityName=e.item;
                console.log(vm.opportunityName.id,'NAme');
                ConfidentialLetters.getSearchOpportunity(vm.opportunityName,onSuccess1,onError1);
            }
        }
        vm.autoCompleteSubject = {
            minimumChars : 1,
            dropdownHeight : '200px',

            data : function(searchText) {
                return $http.get('api/communication-letter/subject').then(
                    function(response) {
                        searchText = searchText.toLowerCase();
                        var states = _.filter(response.data,
                            function(state) {
                                // console.log(state);
                                return (state.subject).toLowerCase()
                                    .startsWith(searchText);

                            });

                        return states;
                    });
            },
            renderItem : function(item) {
                return {
                    value : item,
                    label : $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.subject + "</p>")
                };
            },

            itemSelected : function(e) {

                console.log(e);
                vm.selectedName = e;
                vm.subject = e.item.subject;
                vm.communicationLetters=e.item;
                console.log( vm.communicationLetters.id,'NAme');


                ConfidentialLetters.getSearchSubject(vm.communicationLetters,onSuccess,onError1);
            }
        }
        function onSuccess1(data, headers){
            console.log(data);
            console.log(vm.name);
            vm.communicationLetters = data;

        }
        function onSuccess(data, headers){
            vm.communicationLetters = data;
        }
        function onError1() {
        }
        function clearOpp() {

            vm.name = '';
            vm.communicationLetters=[];
            loadAll();
        }
        function clearSub() {
            vm.subject = '';
            vm.communicationLetters=[];
            loadAll();
        }
        function loadAll() {
            ConfidentialLetters.query({
                page : pagingParams.page - 1,
                size : vm.itemsPerPage,
                sort : sort()
            }, onSuccessFixed, onError);
            $scope.$on('authenticationSuccess', function() {
                getAccount();});

        }
        getAccount();
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

            });

        }
        function onSuccessFixed(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            for (var i = 0; i < data.length; i++) {
                vm.communicationLetters.push(data[i]);

            }
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.communicationLetters=data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

        function sort() {
            var result = [ vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc') ];
            console.log(vm.reverse);
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function reset() {
            vm.page = 0;
            vm.communicationLetters = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {

            $state.transitionTo($state.$current, {
                page : vm.page,
                sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search : vm.currentSearch
            });


        }


    }
})();
