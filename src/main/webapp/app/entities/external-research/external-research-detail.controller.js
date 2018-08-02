(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ExternalResearchDetailController', ExternalResearchDetailController);

    ExternalResearchDetailController.$inject = ['$stateParams', 'Principal','ExternalResearchAnalyst','$scope','$filter','$uibModal'];

    function ExternalResearchDetailController($stateParams,Principal, ExternalResearchAnalyst,$scope,$filter,$uibModal) {
        var vm = this;

        vm.load = load;
        vm.loadAll = loadAll;
        vm.externalResearch;     
        vm.load($stateParams.id);
        
        vm.loadAll();
        
        function loadAll () {      	
      	  
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
       
        var myDate=new Date();
        
         $scope.currentYear = $filter('date')(myDate,'yyyy');
         
         $scope.review = function(){

             var modalInstance = $uibModal.open({

                 templateUrl: 'app/entities/review-external-comment/external-reviews.html',
                 controllerAs: '$ctrl',
                 controller: 'ReviewExternalController',
                 size: 'lg',
                 resolve: {
                     options: function() {
                         return $stateParams;
                     }
                 }

             });

         }
         
        function load(id) {
        
        	ExternalResearchAnalyst.get({id: id}, function(result) {
        		vm.externalResearch = result;
        		console.log(vm.externalResearch);
            });
        }
    }
})();
