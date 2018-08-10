(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('UserDelegationAdminController', UserDelegationAdminController);

    UserDelegationAdminController.$inject = ['UserDelegationAudit','OpportunityMaster','Principal', 'ParseLinks', 'AlertService', 'paginationConstants','entity','$http','$sce','$scope','$filter'];

    function UserDelegationAdminController(UserDelegationAudit,OpportunityMaster,Principal, ParseLinks, AlertService, paginationConstants,entity,$http,$sce,$scope,$filter) {

        var vm = this;
        vm.save=save;
        vm.opportunityMasters=[];

        /*fromName1=['girija','guna','gayu','admin'];*/
        vm.autoCompleteOptions = {
            minimumChars : 1,
            dropdownHeight : '200px',
            data : function(searchText) {
                return $http.get('api/users-All').then(
                    function(response) {


                        searchText = searchText.toLowerCase();



                        // ideally filtering should be done on the server
                        var states = _.filter(response.data,
                            function(state) {
                                return (state.login).toLowerCase()
                                    .startsWith(searchText);

                            });

                        /*  return _.pluck(states, 'sectorType');*/
                        return states;
                    });
                console.log('All data',response);
            },
            renderItem : function(item) {
                return {
                    value : item,
                    label : $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.login + "</p>")
                };
            },

            itemSelected : function(e) {

                console.log(e);
                vm.selectedName = e;
                vm.deleUserName = e.item.login;
                // state.airport = e.item;
            }
        }
        vm.autoCompleteOptionsFrom = {
                minimumChars : 1,
                dropdownHeight : '200px',
                data : function(searchText) {
                    return $http.get('api/users-All').then(
                        function(response) {


                            searchText = searchText.toLowerCase();


                            // ideally filtering should be done on the server
                            var states = _.filter(response.data,
                                function(state) {
                                    return (state.login).toLowerCase()
                                        .startsWith(searchText);

                                });

                            /*  return _.pluck(states, 'sectorType');*/
                            return states;
                        });
                },
                renderItem : function(item) {
                    return {
                        value : item,
                        label : $sce.trustAsHtml("<p class='auto-complete'>"
                            + item.login + "</p>")
                    };
                },

                itemSelected : function(e) {

                    console.log(e);
                    vm.selectedName = e;
                    vm.fromName = e.item.login;
                    // state.airport = e.item;
                }
            }


        vm.autoCompleteOptionsopp = {
            minimumChars : 1,
            dropdownHeight : '200px',
            data : function(searchText) {
                return $http.get('api/opportunity-masters/createdby').then(
                    function(response) {

                        console.log(response);


                        searchText = searchText.toLowerCase();



                        // ideally filtering should be done on the server
                        var states = _.filter(response.data,
                            function(state) {
                                return (state.masterName.oppName).toLowerCase()
                                    .startsWith(searchText);

                            });

                        /*  return _.pluck(states, 'sectorType');*/
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

                console.log(e);
                vm.selectedName = e;
                vm.oppName = e.item.masterName.oppName;

            }
        }


        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });
        
       $scope.fromUser=function(val1){
        	console.log("----",val1);
        	UserDelegationAudit.admin({fromName:val1.login},function(response){
        		console.log("User Opportunities",response);
        		vm.opportunityMasters=response;
        	});
        }

        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');



        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

            });

        }

        function save(){
        	console.log(vm.selectedOpportunity);

            UserDelegationAudit.delegateAdmin({deleUserName:vm.deleUserName, selectedOpportunity:vm.selectedOpportunity,loginName: vm.account.login},function(response){
                console.log(response)
             
            });
        }





    }
})();
