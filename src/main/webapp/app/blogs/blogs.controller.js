(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('BlogController', BlogController);


    BlogController.$inject = ['$scope', 'Principal', 'LoginService', 'AlertService', 'OpportunityMaster','ParseLinks','paginationConstants','$state','$http','$filter','pagingParams'];

    function BlogController ($scope, Principal, LoginService, AlertService, OpportunityMaster,ParseLinks,paginationConstants,$state,$http,$filter,pagingParams) {

        var vm = this;


        Principal.identity().then(function(account) {

            vm.roles = account;

            console.log(vm.roles);



        });


    }
})();


