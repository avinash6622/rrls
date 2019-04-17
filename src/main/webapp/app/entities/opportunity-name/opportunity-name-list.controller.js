(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityNameListController', OpportunityNameListController);

    OpportunityNameListController.$inject = ['OpportunityMaster', 'OpportunityName', 'ParseLinks', 'Principal', 'AlertService', '$timeout', 'paginationConstants', '$scope', '$filter', 'pagingParams', '$state', '$http', '$sce', '$q', '$location'];

    function OpportunityNameListController(OpportunityMaster, OpportunityName, ParseLinks, Principal, AlertService, $timeout, paginationConstants, $scope, $filter, pagingParams, $state, $http, $sce, $q, $location) {
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
        vm.opportunityNme = '';
        vm.name = '';
        vm.clear = clear;
        vm.deleteOpportunityName = deleteOpportunityName;
        vm.deleteMapping = false;
        vm.type = $location.search().type ? $location.search().type : 'manual';
        console.log('$location.search().type -');
        console.log($location);

        vm.itemsValue = 'Opportunities';
//        vm.toggleName='';
        vm.oppoNameChange=oppoNameChange;
        vm.list='';
        // console.log('vm.itemsPerPage -' + vm.itemsPerPage);

        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                // console.log(Principal.isAuthenticated);
            });

        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
  console.log('page -'+vm.page +'type - '+vm.type);
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch,
                type: vm.type
            });
        }

        vm.loadAll();


        function loadAll() {
        console.log(vm.type);

//        console.log($location.search());
//        vm.type = $location.search().type ? $location.search().type : 'manual';
//        vm.type = vm.type ? vm.type : 'manual';

//             var radioValue = $("input[name='name']:checked").val();
           vm.list=vm.type;
           console.log(vm.list);
            if(vm.type == 'manual'){
            console.log('manual');
             OpportunityName.createdByNotNull({
                            page: pagingParams.page - 1,
                            size: vm.itemsPerPage,
                            sort: sort(),
                            type: vm.type
                        }, onSuccess, onError);

            }else{
            console.log('imported');
             OpportunityName.createdByNull({
                            page: pagingParams.page - 1,
                            size: vm.itemsPerPage,
                            sort: sortNull(),
                            type: vm.type
                        }, onSuccessNull, onErrorNull);
            }
        }

        function onSuccess(data, headers) {
             console.log('Manual');
            // console.log(headers('link'));
            // console.log(headers('X-Total-Count'));
            // console.log(pagingParams.page);
//            console.log('vm.opportunityNames');
//            console.log(data.length);
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.opportunityNames = data;
        }

        function onError(error) {
        console.log('error');
        console.log(error);
            AlertService.error(error.data.message);
        }
        function onSuccessNull(data, headers) {
          console.log('Imported');
            // console.log(headers('link'));
            // console.log(headers('X-Total-Count'));
            // console.log(pagingParams.page);
//            console.log('vm.opportunityNames');
//            console.log(data.length);
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.opportunityNames = data;
        }

        function onErrorNull(error) {
            AlertService.error(error.data.message);
        }

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
//            console.log('vm.predicate');
//            console.log(vm.predicate);
            if (vm.predicate !== 'id') {
                result.push('id');
            }
//            console.log('sort result');
//            console.log(result);
            return result;
        }

        function sortNull() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
//            console.log('vm.predicate');
//            console.log(vm.predicate);
            if (vm.predicate !== 'id') {
                result.push('id');
            }
//            console.log('sort result');
//            console.log(result);
            return result;
        }
        vm.autoCompleteOpportunity = {
            minimumChars: 1,
            dropdownHeight: '200px',
            data: function (searchText) {
                return $http.get('/api/opportunity-name-all').then(
                    function (response) {
                        searchText = searchText.toLowerCase();
                        var states = _.filter(response.data,
                            function (state) {
                                // console.log('state');
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

                // console.log(e.item);
                vm.selectedName = e;
                vm.name = e.item.oppName;
                vm.opportunityNme = e.item;
                console.log('vm.opportunityNme -');
                console.log(vm.opportunityNme);
                vm.opportunityNames = [vm.opportunityNme];
                // OpportunityMaster.getSearchOpportunity(vm.opportunityNme, onSuccess1, onError1);
            }
        }

        function onSuccess1(data, headers) {
            console.log('data');
            console.log(data);
            // vm.opportunityNames = data;
            // vm.opportunityNames = [];
            // for (let i = 0; i < data.length; i++) {
            //     vm.opportunityNames.push(data[i].masterName);
            // }
        }

        function onError1() {
        }

        function clear() {
            vm.name = '';
            loadAll();
        }

        function deleteSuccess(data, headers) {
            console.log('deleteSuccess');
            console.log(data);
        }

        function deleteError() {
            console.log('delete error');
        }

        function deleteOpportunityName(opportunityName) {
            console.log('opportunityName in delete');
            console.log(opportunityName);
            return $http.delete('/api/opportunity-name/' + opportunityName.id).then(
                function (response) {
                    console.log('response in delete');
                    console.log(response);
                    vm.loadAll();
                }).catch(function (error) {
                if (error.status === 400) {
                    AlertService.error('Opportunity name already in use');
                }
            })
        }

        function oppoNameChange(){
//        vm.loadAll();
//         var radioValue = $("input[name='name']:checked").val();
//         console.log('last category - '+vm.type);
//         console.log('current category - '+radioValue);
         if(vm.list!=vm.type){
         vm.loadPage(1);
//         pagingParams.page=1;
         vm.loadAll();
         } else{
             vm.loadAll();
         }
        }
    }
})();

